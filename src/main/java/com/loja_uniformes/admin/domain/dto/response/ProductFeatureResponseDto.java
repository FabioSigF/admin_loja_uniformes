package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.product.ProductFeatureEntity;

import java.util.UUID;

public record ProductFeatureResponseDto(UUID id,
                                        String color,
                                        String size,
                                        Double price,
                                        int stockQuantity,
                                        Boolean isAvailable,
                                        Boolean isDeleted,
                                        UUID productId) {

    public static ProductFeatureResponseDto toProductFeatureResponseDto(ProductFeatureEntity productFeature) {
        return new ProductFeatureResponseDto(
                productFeature.getId(),
                productFeature.getColor(),
                productFeature.getSize(),
                productFeature.getPrice(),
                productFeature.getStockQuantity(),
                productFeature.getAvailable(),
                productFeature.getDeleted(),
                productFeature.getProduct().getId()
        );
    }
}
