package com.smartcommerce.auth.dto.request;

public record OAuthUserRequest(
        String email,
        String provider
) {}
