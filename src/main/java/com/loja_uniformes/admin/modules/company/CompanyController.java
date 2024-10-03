package com.loja_uniformes.admin.modules.company;

import com.loja_uniformes.admin.domain.dto.CompanyDto;
import com.loja_uniformes.admin.domain.entity.postgres.CompanyEntity;
import com.loja_uniformes.admin.domain.enums.CompanyCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<CompanyEntity>> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyEntity> getCompanyById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<CompanyEntity>> getAllCompaniesByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompaniesByName(name));
    }

    @GetMapping("/search-category/{category}")
    public ResponseEntity<List<CompanyEntity>> getAllCompaniesByCategory(@PathVariable CompanyCategoryEnum category) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompaniesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<CompanyEntity> saveCompany(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.saveCompany(companyDto));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body("Empresa excluída com sucesso.");
    }
}
