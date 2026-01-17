package com.e_commerce.product_service.dto.request;

import com.e_commerce.product_service.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateRequest(

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock quantity is required")
        @Positive(message = "Stock quantity must be positive")
        Integer stockQuantity,

        @NotNull(message = "Currency is required")
        Currency currency

) {}
