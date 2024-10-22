package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.sale.SaleEntity;
import com.loja_uniformes.admin.domain.entity.sale.SaleItemEntity;
import com.loja_uniformes.admin.modules.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SaleResponseDto(
        UUID id,
        CompanySummaryResponseDto company,
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
                sale.getCreatedAt(),
                sale.getUpdatedAt(),
                sale.getDeleted(),
                saleItems,
                price
        );
    }
}
