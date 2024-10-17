package com.loja_uniformes.admin.domain.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record SaleResponseDto(
        UUID id,
        UUID companyId,
        Instant createdAt,
        Instant updatedAt,
        Boolean deleted,
        Set<SaleItemResponseDto> saleItems
) {
}
