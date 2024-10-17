package com.loja_uniformes.admin.domain.dto.request;

import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.valueobject.PhoneVo;

import java.util.Set;

public record CompanyRequestDto(
        String name,
        String cnpj,
        CompanyCategoryEnum category,
        Set<PhoneVo> phones,
        Set<ProductEntity> products
) {
}
