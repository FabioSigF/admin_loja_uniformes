package com.loja_uniformes.admin.domain.company.enums;

import lombok.Getter;

@Getter
public enum CompanyCategoryEnum {
    ALIMENTACAO ("Alimentação e Bebidas"),
    CONSTRUCAO ("Construção"),
    EDUCACAO("Educação"),
    ENTRETENIMENTO("Entretenimento"),
    INFORMATICA ("Informática"),
    SAUDE ("Saúde"),
    SERVICOSESP ("Serviços especializados"),
    SERVICOSPES ("Serviços pessoais"),
    VENDAS ("Vendas e marketing"),
    VESTUARIO ("Vestuário e calçados"),
    OUTROS ("Outros");

    private final String description;

    CompanyCategoryEnum(String description) {
        this.description = description;
    }
}
