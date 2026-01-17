package com.smartcommerce.user_service.dto.response;


import com.smartcommerce.user_service.enums.Role;

public record AuthenticateUserResponse(
        String email,
        Role role,
        boolean enabled
) {}
