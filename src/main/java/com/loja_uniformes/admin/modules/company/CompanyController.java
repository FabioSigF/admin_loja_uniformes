package com.loja_uniformes.admin.modules.company;

import com.loja_uniformes.admin.domain.dto.request.CompanyRequestDto;
import com.loja_uniformes.admin.domain.dto.response.SaleResponseDto;
import com.loja_uniformes.admin.domain.entity.company.CompanyEntity;
import com.loja_uniformes.admin.domain.dto.response.CompanyResponseDto;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies());
    }

    @GetMapping("/pagination/")
    public ResponseEntity<Page<CompanyResponseDto>> getAllCompaniesWithPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Page<CompanyResponseDto> companies = companyService.getAllCompaniesWithPagination(page, limit);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<CompanyResponseDto>> getAllCompaniesByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompaniesByName(name));
    }

    @GetMapping("/search-category/{category}")
    public ResponseEntity<List<CompanyResponseDto>> getAllCompaniesByCategory(@PathVariable CompanyCategoryEnum category) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompaniesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<CompanyEntity> saveCompany(@RequestBody CompanyRequestDto companyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.saveCompany(companyDto));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body("Empresa excluída com sucesso.");
    }
}
