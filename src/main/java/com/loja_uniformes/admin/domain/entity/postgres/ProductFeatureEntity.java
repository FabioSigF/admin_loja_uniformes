package com.loja_uniformes.admin.domain.entity.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="tb_product_feature")
public class ProductFeatureEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "available")
    @ColumnDefault("true")
    private boolean available;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
