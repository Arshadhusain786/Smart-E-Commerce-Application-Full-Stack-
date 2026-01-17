package com.order_service.order_service.service;

import com.order_service.order_service.client.ProductClient;
import com.order_service.order_service.dto.request.AddToCartRequest;
import com.order_service.order_service.dto.response.ProductResponse;
import com.order_service.order_service.entity.Order;
import com.order_service.order_service.entity.OrderItem;
import com.order_service.order_service.entity.OrderStatus;
import com.order_service.order_service.repository.OrderRepository;
import com.order_service.order_service.util.JwtHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final JwtHelper jwtHelper;
    private final ProductClient productClient;

    /**
     * Create or fetch CART for logged-in user
     */
    @Transactional
    public Order createCartIfNotExists() {

        UUID userId = jwtHelper.getUserId(); // ✅ UUID preferred for distributed systems

        return orderRepository.findByUserIdAndStatus(userId, OrderStatus.CART)
                .orElseGet(() -> {
                    Order order = new Order();
                    order.setUserId(userId);
                    order.setStatus(OrderStatus.CART);
                    order.setItems(new ArrayList<>()); // ✅ IMPORTANT
                    order.setTotalAmount(BigDecimal.ZERO);
                    order.setCreatedAt(Instant.now());
                    order.setUpdatedAt(Instant.now());
                    return orderRepository.save(order);
                });
    }

    /**
     * Add item to cart
     */
    @Transactional
    public Order addItem(AddToCartRequest request) {

        // 1️⃣ Fetch cart
        Order cart = createCartIfNotExists();

        // 2️⃣ Fetch product from Product Service
        ProductResponse product = productClient.getProduct(request.productId());

        if (product == null ) {
            throw new RuntimeException("Product not available");
        }

        // 3️⃣ Check if product already exists in cart
        Optional<OrderItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(product.id()))
                .findFirst();

        if (existingItem.isPresent()) {
            // 🔁 Increase quantity
            OrderItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            item.setSubtotal(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        } else {
            // ➕ Add new item
            OrderItem item = new OrderItem();
            item.setProductId(product.id());
               // ✅ snapshot
            item.setPrice(product.price());          // ✅ snapshot
            item.setQuantity(request.quantity());
            item.setSubtotal(
                    product.price()
                            .multiply(BigDecimal.valueOf(request.quantity()))
            );
            item.setOrder(cart);
            cart.getItems().add(item);
        }

        // 4️⃣ Recalculate cart total
        recalculate(cart);

        return orderRepository.save(cart);
    }

    /**
     * Recalculate cart total
     */
    private void recalculate(Order order) {
        BigDecimal total = order.getItems().stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);
        order.setUpdatedAt(Instant.now());
    }

    /**
     * Place order
     */
    @Transactional
    public Order placeOrder() {

        Order cart = createCartIfNotExists();

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        cart.setStatus(OrderStatus.PLACED);
        cart.setUpdatedAt(Instant.now());

        return orderRepository.save(cart);
    }
}
