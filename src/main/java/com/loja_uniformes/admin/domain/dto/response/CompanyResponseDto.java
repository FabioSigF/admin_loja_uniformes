package com.loja_uniformes.admin.domain.dto.response;

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
}
