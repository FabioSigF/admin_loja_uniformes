package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.enums.ProductGenderEnum;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductResponseDto(UUID id,
                                 UUID companyId,
                                 String name,
                                 String description,
                                 ProductGenderEnum gender,
                                 Set<ProductFeatureResponseDto> features
) {
    public static ProductResponseDto toProductResponseDto(ProductEntity product) {

        Set<ProductFeatureResponseDto> productFeatures = product.getProductFeatures().stream()
                .map(ProductFeatureResponseDto::toProductFeatureResponseDto).collect(Collectors.toSet());

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
