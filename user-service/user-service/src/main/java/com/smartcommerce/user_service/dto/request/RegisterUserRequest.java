package com.smartcommerce.user_service.dto.request;

public record RegisterUserRequest(
        String email,
        String password
) {}
