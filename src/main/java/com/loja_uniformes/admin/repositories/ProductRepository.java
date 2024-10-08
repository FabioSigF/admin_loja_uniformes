package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findOneByIdAndDeletedFalse(UUID id);
    Optional<List<ProductEntity>> findAllByCompanyIdAndDeletedFalse(UUID id);
    Optional<List<ProductEntity>> findAllByCompanyIdAndNameContainingIgnoreCaseAndDeletedFalse(UUID id,String name);
}
