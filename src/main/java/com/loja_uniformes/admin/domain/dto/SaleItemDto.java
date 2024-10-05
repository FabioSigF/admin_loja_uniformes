package com.loja_uniformes.admin.domain.dto;

import com.loja_uniformes.admin.domain.entity.postgres.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.entity.postgres.SaleEntity;

import java.util.UUID;

public record SaleItemDto(UUID saleId,
                          UUID productFeatureId,
                          double price,
                          int amount) {
}
