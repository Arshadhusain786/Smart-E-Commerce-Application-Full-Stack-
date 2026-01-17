package com.order_service.order_service.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        BigDecimal price,
        String currency
) {}
