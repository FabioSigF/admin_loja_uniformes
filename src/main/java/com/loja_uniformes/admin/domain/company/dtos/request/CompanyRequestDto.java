package com.loja_uniformes.admin.domain.company.dtos.request;

import com.loja_uniformes.admin.domain.company.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.domain.product.ProductEntity;
import com.loja_uniformes.admin.valueobject.PhoneVo;

import java.time.Instant;
import java.util.Set;

public record CompanyRequestDto(
        String name,
        String cnpj,
        CompanyCategoryEnum category,
        Set<PhoneVo> phones,
        Set<ProductEntity> products
) {
}
