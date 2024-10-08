package com.loja_uniformes.admin.domain.sale.dtos.request;

import java.util.UUID;

public record SaleItemRequestDto(UUID saleId,
                                 UUID productFeatureId,
                                 double price,
                                 int amount) {
}
