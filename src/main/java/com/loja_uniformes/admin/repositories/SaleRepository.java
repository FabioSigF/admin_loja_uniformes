package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.postgres.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<SaleEntity, UUID> {
}
