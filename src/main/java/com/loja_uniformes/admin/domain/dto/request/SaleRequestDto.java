package com.loja_uniformes.admin.domain.dto.request;

import com.loja_uniformes.admin.domain.enums.SaleStatusEnum;

import java.util.Set;
import java.util.UUID;

public record SaleRequestDto(UUID companyId,
                             Set<SaleItemRequestDto> saleItems,
                             SaleStatusEnum status) {
}
