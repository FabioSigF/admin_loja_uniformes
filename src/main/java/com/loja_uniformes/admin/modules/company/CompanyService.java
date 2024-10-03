package com.loja_uniformes.admin.modules.company;

import com.loja_uniformes.admin.domain.dto.CompanyDto;
import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.entity.postgres.ProductEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.modules.product.ProductService;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.utils.validator.CnpjValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private ProductService productService;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // GET METHODS

    public CompanyEntity getCompanyById(UUID id) {
        return companyRepository.findOneByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada com o id fornecido."));
    }

    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAllByDeletedFalse()
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada."));
    }

    public List<CompanyEntity> getAllCompaniesByName(String name) {
        return companyRepository.findAllByNameContainingIgnoreCaseAndDeletedFalse(name)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada com o nome fornecido."));
    }

    public List<CompanyEntity> getAllCompaniesByCategory(CompanyCategoryEnum category) {
        return companyRepository.findAllByCategoryAndDeletedFalse(category)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada na categoria " + category.getDescription() + "."));
    }

    // POST METHODS
    @Transactional
    public CompanyEntity saveCompany(CompanyDto companyDto) {
        CompanyEntity company = new CompanyEntity();

        // Valida se já existe empresa com o mesmo nome
        Optional<CompanyEntity> companyOpt = companyRepository.findOneByNameAndDeletedFalse(companyDto.name());
        if (companyOpt.isPresent()) {
            throw new IllegalArgumentException("Já existe uma empresa com o mesmo nome.");
        }

        //Valida se já existe empresa com o mesmo CNPJ
        Optional<CompanyEntity> companyOptCnpj = companyRepository.findOneByCnpjAndDeletedFalse(companyDto.cnpj());
        if (companyOptCnpj.isPresent()) {
            throw new IllegalArgumentException("Já existe uma empresa com o mesmo CNPJ.");
        }

        // Valida o formato do CNPJ
        if (!CnpjValidator.isValidCNPJ(companyDto.cnpj())) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }

        company.setName(companyDto.name());
        company.setCnpj(companyDto.cnpj());
        company.setCategory(companyDto.category());
        company.setPhones(companyDto.phones());
        company.setProducts(companyDto.products());
        company.setDeleted(false);
        Instant now = Instant.now();
        company.setCreatedAt(now);
        company.setUpdatedAt(now);

        return companyRepository.save(company);
    }

    // DELETE METHOD
    @Transactional
    public void deleteCompany(UUID id) {
        Optional<CompanyEntity> companyOpt = companyRepository.findById(id);

        if (companyOpt.isEmpty()) {
            throw new EntityNotFoundException("A empresa não foi encontrada no sistema.");
        }

        CompanyEntity company = companyOpt.get();
        company.setDeleted(true);

        Set<ProductEntity> companyProducts = company.getProducts();
        companyProducts.forEach(product -> productService.deleteProduct(product.getId()));

        companyRepository.save(company);
    }

}
