package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.valueobject.PhoneVo;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CompanyResponseDto(
        UUID id,
        String name,
        String cnpj,
        CompanyCategoryEnum category,
        Set<PhoneVo> phones,
        Instant createdAt,
        Instant updatedAt
) {
    public static CompanyResponseDto toCompanyResponseDto(CompanyEntity company) {
        return new CompanyResponseDto(
                company.getId(),
                company.getName(),
                company.getCnpj(),
                company.getCategory(),
                company.getPhones(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }
}
