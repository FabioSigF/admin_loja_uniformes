package com.loja_uniformes.admin.modules.company;

import com.loja_uniformes.admin.domain.dto.request.CompanyRequestDto;
import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.dto.response.CompanyResponseDto;
import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.modules.product.ProductService;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.utils.validator.CnpjValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ProductService productService;

    public CompanyService(CompanyRepository companyRepository, ProductService productService) {
        this.companyRepository = companyRepository;
        this.productService = productService;
    }

    // GET METHODS

    public CompanyResponseDto getCompanyById(UUID id) {
        CompanyEntity company = companyRepository.findOneByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada com o id fornecido."));
        return toCompanyResponseDto(company);
    }

    public List<CompanyResponseDto> getAllCompanies() {
        List<CompanyEntity> companies = companyRepository.findAllByDeletedFalse()
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada."));
        return companies.stream().map(this::toCompanyResponseDto).toList();
    }

    public List<CompanyResponseDto> getAllCompaniesByName(String name) {
        List<CompanyEntity> companies = companyRepository.findAllByNameContainingIgnoreCaseAndDeletedFalse(name)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada com o nome fornecido."));
        return companies.stream().map(this::toCompanyResponseDto).toList();
    }

    public List<CompanyResponseDto> getAllCompaniesByCategory(CompanyCategoryEnum category) {
        List<CompanyEntity> companies = companyRepository.findAllByCategoryAndDeletedFalse(category)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada na categoria " + category.getDescription() + "."));
        return companies.stream().map(this::toCompanyResponseDto).toList();
    }

    // POST METHODS
    @Transactional
    public CompanyEntity saveCompany(CompanyRequestDto companyDto) {
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

    //CONVERT METHODS

    public CompanyResponseDto toCompanyResponseDto(CompanyEntity company) {
        return new CompanyResponseDto(
                company.getId(),
                company.getName(),
                company.getCnpj(),
                company.getCategory(),
                company.getPhones(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }
}
