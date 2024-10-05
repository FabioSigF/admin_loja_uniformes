package com.loja_uniformes.admin.modules.sale;

import com.loja_uniformes.admin.domain.dto.SaleDto;
import com.loja_uniformes.admin.domain.entity.postgres.SaleEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
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
    public ResponseEntity<SaleEntity> saveSale(@RequestBody SaleDto saleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.saveSale(saleDto));
    }

    @GetMapping
    public ResponseEntity<List<SaleEntity>> getAllSales() {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleEntity> getSaleById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getSaleById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<SaleEntity>> getAllSalesByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByDateRange(startDate, endDate));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<SaleEntity>> getAllSalesByCompanyId(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyId(id));
    }

    @GetMapping("/company/{id}/by-date-range")
    public ResponseEntity<List<SaleEntity>> getAllSalesByCompanyIdAndDateRange(
            @PathVariable UUID id,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyIdAndDateRange(id, startDate, endDate));
    }

    @GetMapping("/company/{id}/by-date")
    public ResponseEntity<List<SaleEntity>> getAllSalesByCompanyIdAndDate(
            @PathVariable UUID id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyIdAndDate(id, date));
    }

    @GetMapping("/category/")
    public ResponseEntity<List<SaleEntity>> getAllSalesByCompanyCategoryAndDateRange(
            @RequestParam("category") CompanyCategoryEnum category,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSalesByCompanyCategoryAndDateRange(category, startDate, endDate));
    }


}
