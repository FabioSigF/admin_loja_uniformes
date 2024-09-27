package com.loja_uniformes.admin.domain.entity.postgres;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.domain.valueobject.PhoneVo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tb_company")
public class CompanyEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CompanyCategoryEnum category;

    @ElementCollection
    @CollectionTable(name = "tb_company_phones", joinColumns = @JoinColumn(name = "company_id"))
    private Set<PhoneVo> phones = new HashSet<>();

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private Boolean deleted;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<ProductEntity> products = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<SaleEntity> sales = new HashSet<>();

}
