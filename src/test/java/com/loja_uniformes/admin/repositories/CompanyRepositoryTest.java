package com.loja_uniformes.admin.repositories;

import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.dto.request.CompanyRequestDto;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.valueobject.PhoneVo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
class CompanyRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    CompanyRepository companyRepository;

    @Test
    @DisplayName("Should not return a list of companies from db.")
    void findAllByDeletedFalseError() {
        Optional<List<CompanyEntity>> result = this.companyRepository.findAllByDeletedFalse();

        Assertions.assertTrue(result.isPresent(), "O resultado deve estar presente.");
        Assertions.assertTrue(result.get().isEmpty(), "A lista de empresas deve estar vazia.");
    }

    @Test
    @DisplayName("Should return a list of companies successfully from db.")
    void findAllByDeletedFalseSuccess() {
        PhoneVo phone = new PhoneVo("32236110", false);
        Set<PhoneVo> phoneList = new HashSet<>();
        phoneList.add(phone);

        CompanyRequestDto data = new CompanyRequestDto(
                "Empresa",
                "51128317000149",
                CompanyCategoryEnum.EDUCACAO,
                phoneList,
                new HashSet<>()
        );

        this.createCompany(data);

        Optional<List<CompanyEntity>> result = this.companyRepository.findAllByDeletedFalse();

        Assertions.assertTrue(result.isPresent(), "O resultado deve estar presente.");
        Assertions.assertFalse(result.get().isEmpty(), "A lista de empresas não deve estar vazia.");

        Assertions.assertEquals(1, result.get().size(), "Deve haver exatamente uma empresa na lista.");
        Assertions.assertEquals("Empresa", result.get().get(0).getName(), "O nome da empresa deve corresponder.");
        Assertions.assertEquals("51128317000149", result.get().get(0).getCnpj(), "O CNPJ deve corresponder.");
    }

    private CompanyEntity createCompany(CompanyRequestDto companyDto) {
        CompanyEntity company = new CompanyEntity();

        company.setName(companyDto.name());
        company.setCnpj(companyDto.cnpj());
        company.setCategory(companyDto.category());
        company.setPhones(companyDto.phones());
        company.setProducts(companyDto.products());
        company.setDeleted(false);
        Instant now = Instant.now();
        company.setCreatedAt(now);
        company.setUpdatedAt(now);

        this.entityManager.persist(company);

        return company;
    }
}