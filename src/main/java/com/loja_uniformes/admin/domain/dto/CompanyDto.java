package com.loja_uniformes.admin.domain.dto;

import com.loja_uniformes.admin.domain.entity.postgres.ProductEntity;
import com.loja_uniformes.admin.domain.entity.postgres.SaleEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.domain.valueobject.PhoneVo;

import java.time.Instant;
import java.util.Set;

public record CompanyDto(
        String name,
        String cnpj,
        CompanyCategoryEnum category,
        Set<PhoneVo> phones,
        Set<ProductEntity> products,
        Instant createdAt,
        Instant updatedAt
) {
}
