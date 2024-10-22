package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.sale.SaleEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SaleSummaryResponseDto(
        UUID id,
        UUID companyId,
        Instant createdAt,
        Instant updatedAt,
        Boolean deleted,
        Set<SaleItemResponseDto> saleItems
) {
    public static SaleSummaryResponseDto toSaleSummaryResponseDto(SaleEntity sale) {

        Set<SaleItemResponseDto> saleItems = sale.getSaleItems().stream()
                .map(SaleItemResponseDto::toSaleItemResponseDto).collect(Collectors.toSet());


        return new SaleSummaryResponseDto(
                sale.getId(),
                sale.getCompany().getId(),
                sale.getCreatedAt(),
                sale.getUpdatedAt(),
                sale.getDeleted(),
                saleItems
        );
    }
}
