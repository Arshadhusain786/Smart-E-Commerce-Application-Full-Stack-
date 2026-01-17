package com.order_service.order_service.repository;

import com.order_service.order_service.entity.Order;
import com.order_service.order_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    Optional<Order> findByUserIdAndStatus(UUID userId, OrderStatus status);


}
