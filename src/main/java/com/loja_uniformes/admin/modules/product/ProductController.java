package com.loja_uniformes.admin.modules.product;

import com.loja_uniformes.admin.domain.dto.request.ProductRequestDto;
import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.dto.response.ProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsByCompanyId(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByCompanyId(id));
    }

    @GetMapping("/company/{id}/")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsByCompanyName(
            @PathVariable UUID id,
            @RequestParam("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductsByCompanyIdAndName(id, name));
    }

    @PostMapping
    public ResponseEntity<ProductEntity> saveProduct(@RequestBody ProductRequestDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDto));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }
}
