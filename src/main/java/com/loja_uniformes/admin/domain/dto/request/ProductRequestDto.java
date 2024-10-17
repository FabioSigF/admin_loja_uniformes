package com.loja_uniformes.admin.domain.dto.request;

import com.loja_uniformes.admin.domain.entity.product.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.enums.ProductGenderEnum;

import java.util.Set;
import java.util.UUID;

public record ProductRequestDto(UUID companyId,
                                String name,
                                String description,
                                ProductGenderEnum gender,
                                Set<ProductFeatureEntity> features
) {
}
