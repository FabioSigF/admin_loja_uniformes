package com.loja_uniformes.admin.modules.sale;

import com.loja_uniformes.admin.domain.company.dtos.response.CompanyResponseDto;
import com.loja_uniformes.admin.domain.sale.dtos.request.SaleRequestDto;
import com.loja_uniformes.admin.domain.sale.dtos.request.SaleItemRequestDto;
import com.loja_uniformes.admin.domain.company.CompanyEntity;
import com.loja_uniformes.admin.domain.sale.SaleEntity;
import com.loja_uniformes.admin.domain.company.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.domain.sale.dtos.response.SaleItemResponseDto;
import com.loja_uniformes.admin.domain.sale.dtos.response.SaleResponseDto;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.repositories.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final CompanyRepository companyRepository;
    private final SaleItemService saleItemService;

    public SaleService(SaleRepository saleRepository, CompanyRepository companyRepository, SaleItemService saleItemService) {
        this.saleRepository = saleRepository;
        this.companyRepository = companyRepository;
        this.saleItemService = saleItemService;
    }

    //
    // GET METHODS

    public List<SaleResponseDto> getAllSales() {
        List<SaleEntity> sales = saleRepository.findAllByDeletedFalse().
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda enconstrada."));

        return sales.stream().map(this::toSaleResponseDto).toList();
    }

    public SaleResponseDto getSaleById(UUID id) {
        SaleEntity sale = saleRepository.findOneByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada com o id fornecido."));
        return toSaleResponseDto(sale);
    }

    public List<SaleResponseDto> getAllSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        Instant startInstant = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = endDate.atTime(23, 59, 59).atZone(ZoneOffset.UTC).toInstant();

        List<SaleEntity> sales = saleRepository.findAllByCreatedAtBetweenAndDeletedFalse(startInstant, endInstant).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada durante essas datas."));
        return sales.stream().map(this::toSaleResponseDto).toList();
    }

    public List<SaleResponseDto> getAllSalesByCompanyId(UUID id) {
        List<SaleEntity> sales = saleRepository.findAllByCompanyIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada na empresa."));
        return sales.stream().map(this::toSaleResponseDto).toList();
    }


    public List<SaleResponseDto> getAllSalesByCompanyIdAndDateRange(UUID id, LocalDate startDate, LocalDate endDate) {
        Instant startInstant = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = endDate.atTime(23, 59, 59).atZone(ZoneOffset.UTC).toInstant();
        List<SaleEntity> sales = saleRepository.findAllByCompanyIdAndCreatedAtBetweenAndDeletedFalse(id, startInstant, endInstant).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada na empresa durante essas data."));

        System.out.println("Start Instant: " + startInstant);
        System.out.println("End Instant: " + endInstant);
        return sales.stream().map(this::toSaleResponseDto).toList();
    }

    public List<SaleResponseDto> getAllSalesByCompanyIdAndDate(UUID id, LocalDate createdAt) {
        Instant createdAtInstant = createdAt.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = createdAt.atTime(23, 59, 59).atZone(ZoneOffset.UTC).toInstant();

        List<SaleEntity> sales = saleRepository.findAllByCompanyIdAndCreatedAtBetweenAndDeletedFalse(id, createdAtInstant, endInstant).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada na empresa nessa data."));
        return sales.stream().map(this::toSaleResponseDto).toList();
    }

    public List<SaleResponseDto> getAllSalesByCompanyCategoryAndDateRange(CompanyCategoryEnum category, LocalDate startDate, LocalDate endDate) {
        Instant startInstant = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = endDate.atTime(23, 59, 59).atZone(ZoneOffset.UTC).toInstant();
        List<SaleEntity> sales = saleRepository.findAllByCompanyCategoryAndCreatedAtBetweenAndDeletedFalse(category, startInstant, endInstant).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada por empresas dessa categoria e durante essas datas."));
        return sales.stream().map(this::toSaleResponseDto).toList();
    }

    // POST METHODS
    public SaleEntity saveSale(SaleRequestDto saleDto) {
        if (saleDto.companyId() == null) {
            throw new IllegalArgumentException("O ID da empresa não pode ser nulo.");
        }

        // Valida se a empresa existe
        Optional<CompanyEntity> companyOpt = companyRepository.findOneByIdAndDeletedFalse(saleDto.companyId());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("Empresa não encontrada.");
        }

        if (saleDto.saleItems() == null || saleDto.saleItems().isEmpty())
            throw new IllegalArgumentException("Não é possível realizar uma venda sem itens no carrinho.");
        ;

        SaleEntity sale = new SaleEntity();

        Instant now = Instant.now();
        sale.setCreatedAt(now);
        sale.setUpdatedAt(now);
        sale.setCompany(companyOpt.get());

        // Salva a venda
        SaleEntity savedSale = saleRepository.save(sale);
        saleRepository.flush(); // Força a persistência para garantir que o ID esteja disponível

        // Adicionando itens da venda

        saleDto.saleItems().forEach(item -> {
            SaleItemRequestDto itemDto = new SaleItemRequestDto(
                    savedSale.getId(),
                    item.productFeatureId(),
                    item.price(),
                    item.amount()
            );
            saleItemService.saveSaleItem(itemDto);
        });

        return savedSale;

    }

    // DELETE METHOD
    @Transactional
    public void deleteSale(UUID id) {
        Optional<SaleEntity> saleOpt = saleRepository.findOneByIdAndDeletedFalse(id);

        if (saleOpt.isEmpty()) {
            throw new EntityNotFoundException("A venda não foi encontrada no sistema.");
        }

        SaleEntity sale = saleOpt.get();
        sale.setDeleted(true);

        saleRepository.save(sale);
    }

    // CONVERT METHOD
    public SaleResponseDto toSaleResponseDto(SaleEntity sale) {

        Set<SaleItemResponseDto> saleItems = sale.getSaleItems().stream()
                .map(saleItemService::toSaleItemResponseDto).collect(Collectors.toSet());

        return new SaleResponseDto(
                sale.getId(),
                sale.getCompany().getId(),
                sale.getCreatedAt(),
                sale.getUpdatedAt(),
                sale.getDeleted(),
                saleItems
        );
    }
}
