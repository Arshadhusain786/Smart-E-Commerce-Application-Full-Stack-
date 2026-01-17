package com.order_service.order_service.client;

import com.order_service.order_service.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductResponse getProduct(UUID productId) {
        return restTemplate.getForObject(
                productServiceUrl + "/api/products/" + productId,
                ProductResponse.class
        );
    }
}
