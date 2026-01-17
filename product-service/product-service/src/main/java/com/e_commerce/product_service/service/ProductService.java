package com.e_commerce.product_service.service;

import com.e_commerce.product_service.dto.request.ProductCreateRequest;
import com.e_commerce.product_service.dto.request.ProductUpdateRequest;
import com.e_commerce.product_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductResponse create(ProductCreateRequest request);

    ProductResponse getById(UUID id);

    Page<ProductResponse> getAll(Pageable pageable);

    ProductResponse update(UUID id, ProductUpdateRequest request);

    void deactivate(UUID id);
}
