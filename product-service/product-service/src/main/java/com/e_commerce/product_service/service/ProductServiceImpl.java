package com.e_commerce.product_service.service;

import com.e_commerce.product_service.dto.request.ProductCreateRequest;
import com.e_commerce.product_service.dto.request.ProductUpdateRequest;
import com.e_commerce.product_service.dto.response.ProductResponse;
import com.e_commerce.product_service.entity.Product;
import com.e_commerce.product_service.enums.ProductStatus;
import com.e_commerce.product_service.exception.ProductNotFoundException;
import com.e_commerce.product_service.repository.ProductRepository;
import com.e_commerce.product_service.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public ProductResponse create(ProductCreateRequest request)
    {
        log.info("Creating product {}", request.name());

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .currency(request.currency()) // ✅ IMPORTANT
                .stockQuantity(request.stockQuantity())
                .build();

        repository.save(product);
        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse getById(UUID id) {
        Product product = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public ProductResponse update(UUID id, ProductUpdateRequest request) {
        return null;
    }

    @Override
    public void deactivate(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setActive(false);
        product.setStatus(ProductStatus.DISCONTINUED);

        repository.save(product);

        log.info("Product {} deactivated", id);
    }

}
