package com.loja_uniformes.admin.domain.enums;

public enum UserRoleEnum {
    ADMIN("administrador"),
    USER("usuário");

    private final String descripton;


    UserRoleEnum(String descripton) {
        this.descripton = descripton;
    }
}
