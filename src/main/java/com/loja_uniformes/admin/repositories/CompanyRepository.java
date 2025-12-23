package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    public Optional<List<CompanyEntity>> findAllByDeletedFalse();

    public Optional<Page<CompanyEntity>> findAllByDeletedFalse(Pageable pageable);

    public Optional<List<CompanyEntity>> findAllByDeletedFalseAndCreatedAtBetween(Instant start, Instant end);

    public Optional<CompanyEntity> findOneByIdAndDeletedFalse(UUID id);

    public Optional<CompanyEntity> findOneByNameAndDeletedFalse(String name);

    public Optional<CompanyEntity> findOneByCnpjAndDeletedFalse(String cnpj);

    public Optional<List<CompanyEntity>> findAllByNameContainingIgnoreCaseAndDeletedFalse(String name);

    public Optional<List<CompanyEntity>> findAllByCategoryAndDeletedFalse(CompanyCategoryEnum category);
}
