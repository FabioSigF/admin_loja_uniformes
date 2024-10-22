package com.loja_uniformes.admin.domain.dto.response;

import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.entity.product.ProductFeatureEntity;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public record SaleItemProductResponseDto(UUID productId, //ProductFeatureId
                                         String productName, //Product Name
                                         String color,
                                         String size,
                                         double price,
                                         int amount

) {

    public static SaleItemProductResponseDto toSaleItemProductResponseDto(ProductFeatureEntity productFeature) {

        return new SaleItemProductResponseDto(
                productFeature.getId(),
                productFeature.getName(),
                productFeature.getColor(),
                productFeature.getSize(),
                productFeature.getPrice(),
                productFeature.getStockQuantity()
        );
    }
}
