package com.loja_uniformes.admin.modules.product;

import com.loja_uniformes.admin.domain.dto.ProductDto;
import com.loja_uniformes.admin.domain.dto.ProductFeatureDto;
import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.entity.postgres.ProductEntity;
import com.loja_uniformes.admin.domain.entity.postgres.ProductFeatureEntity;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.repositories.ProductFeatureRepository;
import com.loja_uniformes.admin.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductFeatureRepository productFeatureRepository;
    private final ProductFeatureService productFeatureService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductFeatureRepository productFeatureRepository, CompanyRepository companyRepository, ProductFeatureRepository productFeatureRepository1, ProductFeatureService productFeatureService) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.productFeatureRepository = productFeatureRepository1;
        this.productFeatureService = productFeatureService;
    }

    // GET METHODS

    // POST METHODS
    @Transactional
    public ProductEntity saveProduct(ProductDto productDto) {

        // Validação explícita do companyId
        if (productDto.companyId() == null) {
            throw new IllegalArgumentException("O ID da empresa não pode ser nulo");
        }

        // Valida se a empresa existe
        Optional<CompanyEntity> companyOpt = companyRepository.findOneByIdAndDeletedFalse(productDto.companyId());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }

        ProductEntity product = new ProductEntity();

        product.setCompany(companyOpt.get());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setGender(productDto.gender());
        if (productDto.features() == null || productDto.features().isEmpty()) product.setAvailable(false);

        // Salva o produto
        ProductEntity savedProduct = productRepository.save(product);
        productRepository.flush(); // Força a persistência para garantir que o ID esteja disponível

        // Adicionando características do produto
        if (productDto.features() != null && !productDto.features().isEmpty()) {
            productDto.features().forEach(feature -> {
                ProductFeatureDto featureDto = new ProductFeatureDto(
                        feature.getColor(),
                        feature.getSize(),
                        feature.getPrice(),
                        feature.getStockQuantity(),
                        feature.getAvailable(),
                        feature.getDeleted(),
                        savedProduct.getId() // Assigning savedProduct to the ProductFeatureDto
                );
                productFeatureService.saveProductFeature(featureDto);
            });
        }

        return savedProduct;
    }

    // DELETE METHOD
    @Transactional
    public void deleteProduct(UUID id) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            throw new EntityNotFoundException("O produto não foi encontrado no sistema.");
        }

        ProductEntity product = productOpt.get();
        product.setDeleted(true);

        //Processo para excluir os productFeature de product

        Optional<List<ProductFeatureEntity>> productOptFeatures = productFeatureRepository.findAllProductFeatureEntitiesByProductIdAndDeletedFalse(id);

        if (productOptFeatures.isEmpty()) {
            throw new IllegalArgumentException("O produto não possui características.");
        }

        List<ProductFeatureEntity> productFeatures = productOptFeatures.get();

        productFeatures.forEach(productFeature -> productFeatureService.deleteProductFeature(productFeature.getId()));

        productRepository.save(product);
    }
}
