package com.loja_uniformes.admin.domain.enums;

public enum ProductGenderEnum {
    MASCULINO ("Masculino"), FEMININO ("Feminino"), UNISSEX( "Unissex");

    private final String description;

    ProductGenderEnum(String description) {
        this.description = description;
    }
}
