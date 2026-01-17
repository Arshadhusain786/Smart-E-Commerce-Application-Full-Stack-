package com.smartcommerce.auth.dto.response;

import java.util.UUID;

public record UserInfoResponse(

        UUID id,
        String email,
        String role,
        boolean enabled
) {}

