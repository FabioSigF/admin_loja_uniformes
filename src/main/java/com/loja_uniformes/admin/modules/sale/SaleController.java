package com.loja_uniformes.admin.modules.sale;

import com.loja_uniformes.admin.domain.dto.request.SaleRequestDto;
import com.loja_uniformes.admin.domain.dto.response.SaleResponseDto;
import com.loja_uniformes.admin.domain.entity.sale.SaleEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleEntity> saveSale(@RequestBody SaleRequestDto saleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.saveSale(saleDto));
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> getAllSales() {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> getSaleById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getSaleById(id));
    }

    @GetMapping("/by-date/pagination/")
    public ResponseEntity<Page<SaleResponseDto>> getAllSalesByDateRangeWithPagination(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<SaleResponseDto> sales = saleService.getAllSalesByDateRangeWithPagination(startDate, endDate, page, limit);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/by-date/")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByDateRange(startDate, endDate));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesByCompanyId(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyId(id));
    }

    @GetMapping("/company/{id}/by-date-range/")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesByCompanyIdAndDateRange(
            @PathVariable UUID id,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyIdAndDateRange(id, startDate, endDate));
    }

    @GetMapping("/company/{id}/by-date/")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesByCompanyIdAndDate(
            @PathVariable UUID id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyIdAndDate(id, date));
    }

    @GetMapping("/category/")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesByCompanyCategoryAndDateRange(
            @RequestParam("category") CompanyCategoryEnum category,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyCategoryAndDateRange(category, startDate, endDate));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable UUID id) {
        saleService.deleteSale(id);
        return ResponseEntity.status(HttpStatus.OK).body("Venda deletada com sucesso.");
    }

}
