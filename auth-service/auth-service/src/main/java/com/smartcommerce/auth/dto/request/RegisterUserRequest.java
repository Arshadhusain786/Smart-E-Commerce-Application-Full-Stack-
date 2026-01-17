package com.smartcommerce.auth.dto.request;

public record RegisterUserRequest(
        String email,
        String password,

        String role, String local) {}
