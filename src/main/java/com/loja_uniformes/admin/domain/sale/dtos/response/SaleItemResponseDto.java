package com.loja_uniformes.admin.domain.sale.dtos.response;

import com.loja_uniformes.admin.domain.product.dtos.response.ProductFeatureResponseDto;

import java.util.UUID;

public record SaleItemResponseDto(UUID id,
                                  SaleItemProductResponseDto product,
                                  double price,
                                  int amount) {
}
