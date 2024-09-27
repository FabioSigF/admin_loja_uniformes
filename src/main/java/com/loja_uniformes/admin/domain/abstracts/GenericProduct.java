package com.loja_uniformes.admin.domain.abstracts;

import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.enums.ProductGenderEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class GenericProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private ProductGenderEnum gender;

    @Column(name = "available", nullable = false)
    @ColumnDefault("true")
    private Boolean available;

    @Column(name = "deleted", nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;
}
