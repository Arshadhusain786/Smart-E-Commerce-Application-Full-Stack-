package com.order_service.order_service.dto.response;

import com.order_service.order_service.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        OrderStatus status,
        BigDecimal totalAmount
) {}
