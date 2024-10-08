package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.company.CompanyEntity;
import com.loja_uniformes.admin.domain.company.dtos.response.CompanyResponseDto;
import com.loja_uniformes.admin.domain.company.enums.CompanyCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    public Optional<List<CompanyEntity>> findAllByDeletedFalse();

    public Optional<CompanyEntity> findOneByIdAndDeletedFalse(UUID id);

    public Optional<CompanyEntity> findOneByNameAndDeletedFalse(String name);

    public Optional<CompanyEntity> findOneByCnpjAndDeletedFalse(String cnpj);

    public Optional<List<CompanyEntity>> findAllByNameContainingIgnoreCaseAndDeletedFalse(String name);

    public Optional<List<CompanyEntity>> findAllByCategoryAndDeletedFalse(CompanyCategoryEnum category);
}
