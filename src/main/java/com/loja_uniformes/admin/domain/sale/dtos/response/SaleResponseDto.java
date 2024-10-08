package com.loja_uniformes.admin.domain.sale.dtos.response;

import com.loja_uniformes.admin.domain.company.dtos.request.CompanyRequestDto;
import com.loja_uniformes.admin.domain.sale.dtos.request.SaleItemRequestDto;

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
