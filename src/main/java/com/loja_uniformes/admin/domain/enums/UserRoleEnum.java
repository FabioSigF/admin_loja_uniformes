package com.loja_uniformes.admin.domain.enums;

public enum UserRoleEnum {
    ADMIN("administrador"),
    USER("usuário");

    private final String description;


    UserRoleEnum(String description) {
        this.description = description;
    }
}
