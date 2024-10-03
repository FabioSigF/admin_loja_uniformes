package com.loja_uniformes.admin.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.entity.postgres.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.enums.ProductGenderEnum;

import java.util.Set;
import java.util.UUID;

public record ProductDto(
        UUID companyId,
        String name,
        String description,
        ProductGenderEnum gender,
        Set<ProductFeatureEntity> features
) {
}
