package com.loja_uniformes.admin.modules.sale;


import com.loja_uniformes.admin.domain.entity.product.ProductEntity;
import com.loja_uniformes.admin.domain.dto.request.SaleItemRequestDto;
import com.loja_uniformes.admin.domain.entity.product.ProductFeatureEntity;
import com.loja_uniformes.admin.domain.entity.sale.SaleEntity;
import com.loja_uniformes.admin.domain.entity.sale.SaleItemEntity;
import com.loja_uniformes.admin.domain.dto.response.SaleItemProductResponseDto;
import com.loja_uniformes.admin.domain.dto.response.SaleItemResponseDto;
import com.loja_uniformes.admin.exceptions.EntityNotFoundException;
import com.loja_uniformes.admin.repositories.ProductFeatureRepository;
import com.loja_uniformes.admin.repositories.ProductRepository;
import com.loja_uniformes.admin.repositories.SaleItemRepository;
import com.loja_uniformes.admin.repositories.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SaleItemService {
    private final SaleItemRepository saleItemRepository;
    private final SaleRepository saleRepository;
    private final ProductFeatureRepository productFeatureRepository;

    public SaleItemService(SaleItemRepository saleItemRepository, SaleRepository saleRepository, ProductFeatureRepository productFeatureRepository, ProductRepository productRepository) {
        this.saleItemRepository = saleItemRepository;
        this.saleRepository = saleRepository;
        this.productFeatureRepository = productFeatureRepository;
    }

    @Transactional
    public SaleItemEntity saveSaleItem(SaleItemRequestDto saleItemDto) {

        ProductFeatureEntity productFeature = productFeatureRepository.findOneByIdAndDeletedFalse(saleItemDto.productFeatureId())
                .orElseThrow(() -> new EntityNotFoundException("Produto da venda não encontrado"));

        SaleEntity sale = saleRepository.findOneByIdAndDeletedFalse(saleItemDto.saleId())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada."));

        SaleItemEntity saleItem = new SaleItemEntity();

        saleItem.setPrice(saleItemDto.price());
        saleItem.setAmount(saleItemDto.amount());
        saleItem.setProduct(productFeature);
        saleItem.setSale(sale);

        return saleItemRepository.save(saleItem);
    }




}
