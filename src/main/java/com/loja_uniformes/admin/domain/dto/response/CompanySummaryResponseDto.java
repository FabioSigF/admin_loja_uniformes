package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;

import java.util.UUID;

public record CompanySummaryResponseDto(
        UUID id,
        String name,
        CompanyCategoryEnum category
){
    public static CompanySummaryResponseDto toCompanySummaryDto(CompanyEntity company) {
        return new CompanySummaryResponseDto(
                company.getId(),
                company.getName(),
                company.getCategory()
        );
    }
}
