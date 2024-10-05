package com.loja_uniformes.admin.modules.sale;

import com.loja_uniformes.admin.domain.dto.SaleDto;
import com.loja_uniformes.admin.domain.dto.SaleItemDto;
import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.entity.postgres.SaleEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.CompanyRepository;
import com.loja_uniformes.admin.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    // GET METHODS

    public List<SaleEntity> getAllSales() {
        return saleRepository.findAllByDeletedFalse().
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada."));
    }

    public SaleEntity getSaleById(UUID id) {
        return saleRepository.findOneByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada com o id fornecido."));
    }

    public List<SaleEntity> getAllSalesByDateRange(LocalDate minDate, LocalDate maxDate) {
        return saleRepository.findAllByCreatedAtBetweenAndDeletedFalse(minDate, maxDate).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada durante essas datas."));
    }

    public List<SaleEntity> getAllSalesByCompanyId(UUID id) {
        return saleRepository.findAllByCompanyIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada na empresa."));
    }


    public List<SaleEntity> getAllSalesByCompanyIdAndDateRange(UUID id, LocalDate minDate, LocalDate maxDate) {
        return saleRepository.findAllByCompanyIdAndCreatedAtBetweenAndDeletedFalse(id, minDate, maxDate).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada na empresa durante essas data."));
    }

    public List<SaleEntity> getAllSalesByCompanyIdAndDate(UUID id, LocalDate createdAt) {
        return saleRepository.findAllByCompanyIdAndCreatedAtAndDeletedFalse(id, createdAt).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada na empresa nessa data."));
    }

    public List<SaleEntity> getAllSalesByCompanyCategoryAndDateRange(CompanyCategoryEnum category, LocalDate minDate, LocalDate maxDate) {
        return saleRepository.findAllByCompanyCategoryAndCreatedAtBetweenAndDeletedFalse(category, minDate, maxDate).
                orElseThrow(() -> new EntityNotFoundException("Nenhuma venda encontrada por empresas dessa categoria e durante essas datas."));
    }

    // POST METHODS
    public SaleEntity saveSale(SaleDto saleDto) {
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
            SaleItemDto itemDto = new SaleItemDto(
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
}
