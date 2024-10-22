package com.loja_uniformes.admin.modules.product;

import com.loja_uniformes.admin.domain.dto.request.ProductFeatureRequestDto;
import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.entity.product.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.dto.response.ProductFeatureResponseDto;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.ProductFeatureRepository;
import com.loja_uniformes.admin.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductFeatureService {

    private final ProductFeatureRepository productFeatureRepository;
    private final ProductRepository productRepository;

    public ProductFeatureService(ProductFeatureRepository productFeatureRepository, ProductRepository productRepository) {
        this.productFeatureRepository = productFeatureRepository;
        this.productRepository = productRepository;
    }

    // POST METHODS
    @Transactional
    public ProductFeatureEntity saveProductFeature(ProductFeatureRequestDto productFeatureDto) {
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

        String name = product.getName() + " " + productFeatureDto.size() + " " + productFeatureDto.color();
        productFeature.setName(name);

        productFeature.setPrice(productFeatureDto.price());
        productFeature.setStockQuantity(productFeatureDto.stockQuantity());
        productFeature.setProduct(product);

        return productFeatureRepository.save(productFeature);
    }

    // PATCH METHODS
    @Transactional
    public ProductFeatureResponseDto updateProductFeature(UUID id, ProductFeatureRequestDto dto) {
        ProductFeatureEntity productOpt = productFeatureRepository.findOneByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Característica do produto não foi encontrada."));

        if(dto.color() != null) {
            productOpt.setColor(dto.color());
            String name = productOpt.getName() + " " + productOpt.getSize() + " " + productOpt.getColor();
            productOpt.setName(name);
        }

        if(dto.size() != null) {
            productOpt.setSize(dto.size());
            String name = productOpt.getName() + " " + productOpt.getSize() + " " + productOpt.getColor();
            productOpt.setName(name);
        }

        if(dto.price() != null) {
            productOpt.setPrice(dto.price());
        }

        if(dto.stockQuantity() > -1) {
            productOpt.setStockQuantity(dto.stockQuantity());
        } else {
            throw new IllegalArgumentException("O novo valor de estoque deve ser maior ou igual a 0.");
        }

        productFeatureRepository.save(productOpt);

        return ProductFeatureResponseDto.toProductFeatureResponseDto(productOpt);
    }

    // DELETE METHOD
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
