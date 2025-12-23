package com.loja_uniformes.admin.domain.entity.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.enums.SaleStatusEnum;
import com.loja_uniformes.admin.utils.RandomIDGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(name = "tb_sale")
@AllArgsConstructor
@NoArgsConstructor
public class SaleEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id = RandomIDGenerator.generateRandomID(5);

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SaleStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted", nullable = false)
    @ColumnDefault("false")
    private Boolean deleted = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleItemEntity> saleItems = new HashSet<>();

}
