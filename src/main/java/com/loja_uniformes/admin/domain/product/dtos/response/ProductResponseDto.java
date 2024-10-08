package com.loja_uniformes.admin.domain.product.dtos.response;

import com.loja_uniformes.admin.domain.product.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.product.enums.ProductGenderEnum;

import java.util.Set;
import java.util.UUID;

public record ProductResponseDto(UUID id,
                                 UUID companyId,
                                 String name,
                                 String description,
                                 ProductGenderEnum gender,
                                 Set<ProductFeatureResponseDto> features
) {
}
