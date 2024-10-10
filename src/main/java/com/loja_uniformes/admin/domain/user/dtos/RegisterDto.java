package com.loja_uniformes.admin.domain.user.dtos;

import com.loja_uniformes.admin.domain.user.enums.UserRoleEnum;

public record RegisterDto(String login, String password, UserRoleEnum role) {
}
