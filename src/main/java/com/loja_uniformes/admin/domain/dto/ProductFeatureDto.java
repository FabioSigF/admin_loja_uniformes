package com.loja_uniformes.admin.domain.dto;

import com.loja_uniformes.admin.domain.entity.postgres.ProductEntity;

import java.util.UUID;

public record ProductFeatureDto(String color,
                                String size,
                                Double price,
                                int stockQuantity,
                                Boolean isAvailable,
                                Boolean isDeleted,
                                UUID productId) {
}
