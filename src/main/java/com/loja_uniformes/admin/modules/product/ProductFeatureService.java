package com.loja_uniformes.admin.modules.product;

import com.loja_uniformes.admin.domain.dto.ProductFeatureDto;
import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.entity.postgres.ProductEntity;
import com.loja_uniformes.admin.domain.entity.postgres.ProductFeatureEntity;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.ProductFeatureRepository;
import com.loja_uniformes.admin.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductFeatureService {

    private final ProductFeatureRepository productFeatureRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductFeatureService(ProductFeatureRepository productFeatureRepository, ProductRepository productRepository) {
        this.productFeatureRepository = productFeatureRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductFeatureEntity saveProductFeature(ProductFeatureDto productFeatureDto) {
        ProductFeatureEntity productFeature = new ProductFeatureEntity();

        ProductEntity product = productRepository.findOneByIdAndDeletedFalse(productFeatureDto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        // Valida se a característica do produto já existe
        Optional<ProductFeatureEntity> productFeatureOpt = productFeatureRepository
                .findOneByProductIdAndSizeAndColorAndDeletedFalse(productFeatureDto.productId(),
                        productFeatureDto.size(),
                        productFeatureDto.color());

        if (productFeatureOpt.isPresent()) {
            throw new IllegalArgumentException("Não é permitido criar características repetidas para o mesmo produto.");
        }

        productFeature.setSize(productFeatureDto.size());
        productFeature.setColor(productFeatureDto.color());
        productFeature.setPrice(productFeatureDto.price());
        productFeature.setStockQuantity(productFeatureDto.stockQuantity());
        productFeature.setProduct(product);

        return productFeatureRepository.save(productFeature);
    }

    @Transactional
    public void deleteProductFeature(UUID id) {
        Optional<ProductFeatureEntity> featureOpt = productFeatureRepository.findById(id);

        if (featureOpt.isEmpty()) {
            throw new EntityNotFoundException("A característica do produto não foi encontrada no sistema.");
        }

        ProductFeatureEntity feature = featureOpt.get();
        feature.setDeleted(true);

        productFeatureRepository.save(feature);
    }
}
