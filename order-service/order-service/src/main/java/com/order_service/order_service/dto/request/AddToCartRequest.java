package com.order_service.order_service.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record AddToCartRequest(
        UUID productId,
        Integer quantity
) {}
