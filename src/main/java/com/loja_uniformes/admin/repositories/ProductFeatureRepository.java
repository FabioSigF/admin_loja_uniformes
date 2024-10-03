package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.postgres.ProductFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductFeatureRepository extends JpaRepository<ProductFeatureEntity, UUID> {
    Optional<List<ProductFeatureEntity>> findAllProductFeatureEntitiesByProductIdAndDeletedFalse(UUID id);

    Optional<ProductFeatureEntity> findOneByProductIdAndSizeAndColorAndDeletedFalse(UUID product_id, String size, String color);
}
