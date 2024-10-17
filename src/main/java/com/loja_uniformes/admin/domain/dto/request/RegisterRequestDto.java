package com.loja_uniformes.admin.domain.dto.request;

import com.loja_uniformes.admin.domain.enums.UserRoleEnum;

public record RegisterRequestDto(String login, String password, UserRoleEnum role) {
}
