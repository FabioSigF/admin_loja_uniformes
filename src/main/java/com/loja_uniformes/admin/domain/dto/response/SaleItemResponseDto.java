package com.loja_uniformes.admin.domain.dto.response;

import java.util.UUID;

public record SaleItemResponseDto(UUID id,
                                  SaleItemProductResponseDto product,
                                  double price,
                                  int amount) {
}
