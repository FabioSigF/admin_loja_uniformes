package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.postgres.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItemEntity, UUID> {
}
