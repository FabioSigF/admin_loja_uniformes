package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.sale.SaleEntity;
import com.loja_uniformes.admin.domain.entity.sale.SaleItemEntity;
import com.loja_uniformes.admin.domain.enums.SaleStatusEnum;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record SaleResponseDto(
        String id,
        CompanySummaryResponseDto company,
        SaleStatusEnum status,
        Instant createdAt,
        Instant updatedAt,
        Boolean deleted,
        Set<SaleItemResponseDto> saleItems,
        Double totalPrice
) {

    public static SaleResponseDto toSaleResponseDto(SaleEntity sale) {

        Set<SaleItemResponseDto> saleItems = sale.getSaleItems().stream()
                .map(SaleItemResponseDto::toSaleItemResponseDto).collect(Collectors.toSet());

        Double price = sale.getSaleItems().stream().mapToDouble(SaleItemEntity::getPrice).sum();

        return new SaleResponseDto(
                sale.getId(),
                CompanySummaryResponseDto.toCompanySummaryDto(sale.getCompany()),
                sale.getStatus(),
                sale.getCreatedAt(),
                sale.getUpdatedAt(),
                sale.getDeleted(),
                saleItems,
                price
        );
    }
}
