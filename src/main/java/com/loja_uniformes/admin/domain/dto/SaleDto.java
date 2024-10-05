package com.loja_uniformes.admin.domain.dto;

import com.loja_uniformes.admin.domain.entity.postgres.SaleItemEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record SaleDto(Instant createdAt,
                      Instant updatedAt,
                      UUID companyId,
                      Set<SaleItemDto> saleItems) {
}
