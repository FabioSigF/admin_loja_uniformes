package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.sale.SaleEntity;
import com.loja_uniformes.admin.domain.company.enums.CompanyCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<SaleEntity, UUID> {
    Optional<List<SaleEntity>> findAllByDeletedFalse();

    Optional<List<SaleEntity>> findAllByCreatedAtBetweenAndDeletedFalse(Instant startDate, Instant endDate);

    Optional<List<SaleEntity>> findAllByCompanyIdAndDeletedFalse(UUID companyId);

    Optional<List<SaleEntity>> findAllByCompanyIdAndCreatedAtBetweenAndDeletedFalse(UUID companyId, Instant startDate, Instant endDate);

    Optional<SaleEntity> findOneByIdAndDeletedFalse(UUID id);

    Optional<List<SaleEntity>> findAllByCompanyCategoryAndCreatedAtBetweenAndDeletedFalse(CompanyCategoryEnum category, Instant startDate, Instant endDate);
}
