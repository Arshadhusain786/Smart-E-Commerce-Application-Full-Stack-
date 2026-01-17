package com.smartcommerce.user_service.dto.request;


public record OAuthUserRequest(
        String email,
        String provider
) {}
