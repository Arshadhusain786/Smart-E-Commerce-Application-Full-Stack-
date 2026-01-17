package com.smartcommerce.user_service.dto.response;

import com.smartcommerce.user_service.enums.Role;

import java.util.UUID;

public record UserInfoResponse(
        UUID id,
        String email,
        Role role,
        boolean enabled
) {}

