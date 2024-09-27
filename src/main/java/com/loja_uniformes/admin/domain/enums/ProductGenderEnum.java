package com.loja_uniformes.admin.domain.enums;

public enum ProductGenderEnum {
    MASCULINO ("Masculino"), FEMININO ("Feminino");

    private final String description;

    ProductGenderEnum(String description) {
        this.description = description;
    }
}
