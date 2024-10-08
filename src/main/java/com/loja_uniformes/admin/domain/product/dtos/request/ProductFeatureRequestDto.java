package com.loja_uniformes.admin.domain.product.dtos.request;

import java.util.UUID;

public record ProductFeatureRequestDto(String color,
                                       String size,
                                       Double price,
                                       int stockQuantity,
                                       Boolean isAvailable,
                                       Boolean isDeleted,
                                       UUID productId) {
}
