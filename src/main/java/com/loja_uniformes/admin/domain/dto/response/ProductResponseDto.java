package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.enums.ProductGenderEnum;

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
