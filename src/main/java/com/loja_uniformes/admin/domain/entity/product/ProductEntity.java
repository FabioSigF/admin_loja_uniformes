package com.loja_uniformes.admin.domain.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loja_uniformes.admin.domain.abstracts.GenericProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="tb_product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends GenericProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductFeatureEntity> productFeatures = new HashSet<>();
}
