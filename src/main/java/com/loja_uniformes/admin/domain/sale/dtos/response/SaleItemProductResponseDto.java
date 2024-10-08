package com.loja_uniformes.admin.domain.sale.dtos.response;

import java.util.UUID;

public record SaleItemProductResponseDto(UUID productId, //ProductFeatureId
                                         String productName, //Product Name
                                         String color,
                                         String size,
                                         double price,
                                         int amount

) {
}
