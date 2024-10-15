package com.loja_uniformes.admin.modules.company;

import com.loja_uniformes.admin.domain.company.CompanyEntity;
import com.loja_uniformes.admin.domain.company.dtos.request.CompanyRequestDto;
import com.loja_uniformes.admin.domain.company.dtos.response.CompanyResponseDto;
import com.loja_uniformes.admin.domain.company.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.utils.validator.CnpjValidator;
import com.loja_uniformes.admin.valueobject.PhoneVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    @DisplayName("Should get a company by id successfully.")
    void getCompanyByIdSuccess() {
        UUID companyId = UUID.randomUUID();
        String companyCNPJ = "49486203000129";

        CompanyEntity company = new CompanyEntity(
                companyId,
                "Empresa",
                companyCNPJ,
                CompanyCategoryEnum.EDUCACAO,
                new HashSet<>(),
                Instant.now(),
                Instant.now(),
                false,
                new HashSet<>(),
                new HashSet<>());

        when(companyRepository.findOneByIdAndDeletedFalse(companyId))
                .thenReturn(Optional.of(company));

        CompanyResponseDto result = companyService.getCompanyById(companyId);

        assertNotNull(result);
        assertEquals(companyId, result.id());
        assertEquals(companyCNPJ, result.cnpj());

        //Verifica se o método do repositório foi chamado corretamente
        verify(companyRepository, times(1)).findOneByIdAndDeletedFalse(companyId);
    }

    @Test
    @DisplayName("Should not get a company by an wrong id successfully.")
    void getCompanyByIdError() {
        UUID companyId = UUID.randomUUID();

        when(companyRepository.findOneByIdAndDeletedFalse(companyId))
                .thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> companyService.getCompanyById(companyId));

        assertEquals("Nenhuma empresa encontrada com o id fornecido.", thrown.getMessage());

        verify(companyRepository, times(1)).findOneByIdAndDeletedFalse(companyId);
    }


    @Test
    @DisplayName("Should get a list all companies successfully")
    void getAllCompaniesSuccess() {

        UUID companyId = UUID.randomUUID();

        CompanyEntity company = new CompanyEntity(
                companyId,
                "Empresa",
                "49486203000129",
                CompanyCategoryEnum.EDUCACAO,
                new HashSet<>(),
                Instant.now(),
                Instant.now(),
                false,
                new HashSet<>(),
                new HashSet<>());

        List<CompanyEntity> companiesList = new ArrayList<>();
        companiesList.add(company);

        when(companyRepository.findAllByDeletedFalse()).thenReturn(Optional.of(companiesList));

        List<CompanyResponseDto> result = companyService.getAllCompanies();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(company.getId(), result.get(0).id());

        verify(companyRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    @DisplayName("Should not get a list of companies")
    void getAllCompaniesError() {
        when(companyRepository.findAllByDeletedFalse()).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> companyService.getAllCompanies());

        assertEquals("Nenhuma empresa encontrada.", thrown.getMessage());

        verify(companyRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    @DisplayName("Should create a new company successfully when everything is OK")
    void saveCompany() {
        try (MockedStatic<CnpjValidator> mockedCnpjValidator = mockStatic(CnpjValidator.class)) {

            String companyCNPJ = "49486203000129";

            CompanyRequestDto companyDto = new CompanyRequestDto(
                    "Empresa",
                    companyCNPJ,
                    CompanyCategoryEnum.EDUCACAO,
                    new HashSet<>(),
                    new HashSet<>());

            when(companyRepository.findOneByNameAndDeletedFalse(companyDto.name())).thenReturn(Optional.empty());
            when(companyRepository.findOneByCnpjAndDeletedFalse(companyDto.cnpj())).thenReturn(Optional.empty());

            mockedCnpjValidator.when(() -> CnpjValidator.isValidCNPJ(companyDto.cnpj())).thenReturn(true);

            when(companyRepository.save(any(CompanyEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

            CompanyEntity companyCreated = companyService.saveCompany(companyDto);

            verify(companyRepository, times(1)).save(any());

            verify(companyRepository, times(1)).findOneByNameAndDeletedFalse(companyDto.name());
            verify(companyRepository, times(1)).findOneByCnpjAndDeletedFalse(companyDto.cnpj());

            assertNotNull(companyCreated, "A empresa criada não deve ser nula");
            assertEquals(companyCreated.getCnpj(), companyDto.cnpj(), "O CNPJ deve ser igual ao do DTO");
        }
    }
}