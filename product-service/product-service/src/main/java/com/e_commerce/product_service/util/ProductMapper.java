package com.e_commerce.product_service.util;

import com.e_commerce.product_service.dto.response.ProductResponse;
import com.e_commerce.product_service.entity.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .status(product.getStatus())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}
