package com.loja_uniformes.admin.domain.product.dtos.request;

import com.loja_uniformes.admin.domain.product.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.product.enums.ProductGenderEnum;

import java.util.Set;
import java.util.UUID;

public record ProductRequestDto(UUID companyId,
                                String name,
                                String description,
                                ProductGenderEnum gender,
                                Set<ProductFeatureEntity> features
) {
}
