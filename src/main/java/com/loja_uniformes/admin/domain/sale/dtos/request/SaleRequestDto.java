package com.loja_uniformes.admin.domain.sale.dtos.request;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record SaleRequestDto(Instant createdAt,
                             Instant updatedAt,
                             UUID companyId,
                             Set<SaleItemRequestDto> saleItems) {
}
