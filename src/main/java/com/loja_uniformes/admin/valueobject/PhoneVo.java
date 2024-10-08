package com.loja_uniformes.admin.valueobject;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
public class PhoneVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name="number", nullable = false)
    private final String number;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private final Boolean deleted;


    protected PhoneVo() {
        this.number = null;
        this.deleted = false;
    }

    public PhoneVo(String number, Boolean deleted) {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("O telefone não pode ser nulo ou estar em branco");
        }
        this.number = number;
        this.deleted = (deleted != null) ? deleted : false;
    }
}
