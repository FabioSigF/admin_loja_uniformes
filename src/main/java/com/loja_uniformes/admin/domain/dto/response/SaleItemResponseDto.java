package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.sale.SaleItemEntity;

import java.util.UUID;

public record SaleItemResponseDto(UUID id,
                                  SaleItemProductResponseDto product,
                                  double price,
                                  int amount) {

    public static SaleItemResponseDto toSaleItemResponseDto(SaleItemEntity saleItem) {
        SaleItemProductResponseDto product = SaleItemProductResponseDto.toSaleItemProductResponseDto(saleItem.getProduct());

        return new SaleItemResponseDto(
                saleItem.getId(),
                product,
                saleItem.getPrice(),
                saleItem.getAmount()
        );
    }
}
