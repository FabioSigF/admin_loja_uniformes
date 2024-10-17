package com.loja_uniformes.admin.domain.dto.request;

import java.util.Set;
import java.util.UUID;

public record SaleRequestDto(UUID companyId,
                             Set<SaleItemRequestDto> saleItems) {
}
