package com.loja_uniformes.admin.domain.enums;

public enum SaleStatusEnum {
    PENDENTE("pendente"),
    CANCELADO("cancelado"),
    REEMBOLSADO("reembolsado"),
    COMPLETADO("completado");

    private final String description;

    SaleStatusEnum(String description) {
        this.description = description;
    }
}
