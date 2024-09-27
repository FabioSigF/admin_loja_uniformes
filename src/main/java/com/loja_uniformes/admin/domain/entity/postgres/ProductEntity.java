package com.loja_uniformes.admin.domain.entity.postgres;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_uniformes.admin.domain.abstracts.GenericProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="tb_product")
public class ProductEntity extends GenericProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductFeatureEntity> productFeatures = new HashSet<>();
}
