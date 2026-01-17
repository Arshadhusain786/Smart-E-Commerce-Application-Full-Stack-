package com.smartcommerce.user_service.dto.request;


public record AuthenticateUserRequest(
        String email,
        String password
) {}
