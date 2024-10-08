package com.loja_uniformes.admin.domain.product.dtos.response;

import java.util.UUID;

public record ProductFeatureResponseDto(UUID id,
                                        String color,
                                        String size,
                                        Double price,
                                        int stockQuantity,
                                        Boolean isAvailable,
                                        Boolean isDeleted,
                                        UUID productId) {
}
