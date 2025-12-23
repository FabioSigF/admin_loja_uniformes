package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.sale.SaleEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<SaleEntity, UUID> {
    Optional<List<SaleEntity>> findAllByDeletedFalse();

    Optional<List<SaleEntity>> findAllByDeletedFalseOrderByCreatedAtDesc();

    Optional<List<SaleEntity>> findAllByCreatedAtBetweenAndDeletedFalse(Instant startDate, Instant endDate);

    Optional<Page<SaleEntity>> findAllByCreatedAtBetweenAndDeletedFalse(Instant startDate, Instant endDate, Pageable pageable);

    Optional<List<SaleEntity>> findAllByCompanyIdAndDeletedFalse(UUID companyId);

    Optional<List<SaleEntity>> findAllByCompanyIdAndCreatedAtBetweenAndDeletedFalse(UUID companyId, Instant startDate, Instant endDate);

    Optional<SaleEntity> findOneByIdAndDeletedFalse(String id);

    Optional<List<SaleEntity>> findAllByCompanyCategoryAndCreatedAtBetweenAndDeletedFalse(CompanyCategoryEnum category, Instant startDate, Instant endDate);
}
