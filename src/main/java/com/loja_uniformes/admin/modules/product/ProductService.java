package com.loja_uniformes.admin.modules.product;

import com.loja_uniformes.admin.domain.dto.request.ProductRequestDto;
import com.loja_uniformes.admin.domain.dto.request.ProductFeatureRequestDto;
import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.entity.product.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.dto.response.ProductFeatureResponseDto;
import com.loja_uniformes.admin.domain.dto.response.ProductResponseDto;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.repositories.ProductFeatureRepository;
import com.loja_uniformes.admin.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductFeatureRepository productFeatureRepository;
    private final ProductFeatureService productFeatureService;

    public ProductService(ProductRepository productRepository, ProductFeatureRepository productFeatureRepository, CompanyRepository companyRepository, ProductFeatureRepository productFeatureRepository1, ProductFeatureService productFeatureService) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.productFeatureRepository = productFeatureRepository1;
        this.productFeatureService = productFeatureService;
    }

    // GET METHODS
    public ProductResponseDto getProductById(UUID id) {
        ProductEntity product = productRepository.findOneByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        return toProductResponseDto(product);
    }

    public List<ProductResponseDto> getAllProductsByCompanyId(UUID id) {
        List<ProductEntity> products = productRepository.findAllByCompanyIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        return products.stream().map(this::toProductResponseDto).toList();
    }

    public List<ProductResponseDto> getAllProductsByCompanyIdAndName(UUID companyId, String name) {
        List<ProductEntity> products = productRepository.findAllByCompanyIdAndNameContainingIgnoreCaseAndDeletedFalse(companyId, name)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado;"));

        return products.stream().map(this::toProductResponseDto).toList();
    }

    // POST METHODS
    @Transactional
    public ProductEntity saveProduct(ProductRequestDto productDto) {

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
                ProductFeatureRequestDto featureDto = new ProductFeatureRequestDto(
                        feature.getColor(),
                        feature.getSize(),
                        feature.getPrice(),
                        feature.getStockQuantity(),
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

    // CONVERT METHOD
    public ProductResponseDto toProductResponseDto(ProductEntity product) {

        Set<ProductFeatureResponseDto> productFeatures = product.getProductFeatures().stream()
                .map(productFeatureService::toProductFeatureResponseDto).collect(Collectors.toSet());

        return new ProductResponseDto(
                product.getId(),
                product.getCompany().getId(),
                product.getName(),
                product.getDescription(),
                product.getGender(),
                productFeatures
        );
    }
}
