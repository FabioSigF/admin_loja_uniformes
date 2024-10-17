package com.loja_uniformes.admin.domain.dto.request;

import java.util.UUID;

public record SaleItemRequestDto(UUID saleId,
                                 UUID productFeatureId,
                                 double price,
                                 int amount) {
}
