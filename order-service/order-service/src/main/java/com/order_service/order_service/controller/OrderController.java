package com.order_service.order_service.controller;


import com.order_service.order_service.dto.request.AddToCartRequest;
import com.order_service.order_service.dto.request.CreateOrderRequest;
import com.order_service.order_service.dto.response.OrderResponse;
import com.order_service.order_service.entity.Order;
import com.order_service.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/cart")
    public OrderResponse addToCart(@RequestBody AddToCartRequest request) {
        Order order = service.addItem(request);
        return map(order);
    }

    @PostMapping("/place")
    public OrderResponse placeOrder() {
        Order order = service.placeOrder();
        return map(order);
    }

    private OrderResponse map(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount()
        );
    }
}
