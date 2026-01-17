package com.e_commerce.product_service.dto.response;

import com.e_commerce.product_service.enums.Currency;
import com.e_commerce.product_service.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class ProductResponse {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private ProductStatus status;
    private Integer stockQuantity;
}
