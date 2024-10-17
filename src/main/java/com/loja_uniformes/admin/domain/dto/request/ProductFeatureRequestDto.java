package com.loja_uniformes.admin.domain.dto.request;

import java.util.UUID;

public record ProductFeatureRequestDto(String color,
                                       String size,
                                       Double price,
                                       int stockQuantity,
                                       UUID productId) {
}
