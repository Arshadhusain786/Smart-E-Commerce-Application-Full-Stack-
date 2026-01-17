package com.smartcommerce.auth.service;

import com.smartcommerce.auth.client.UserServiceClient;
import com.smartcommerce.auth.dto.request.LoginRequest;
import com.smartcommerce.auth.dto.request.RegisterUserRequest;
import com.smartcommerce.auth.dto.response.JwtResponse;
import com.smartcommerce.auth.dto.response.UserInfoResponse;
import com.smartcommerce.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceClient userServiceClient;
    private final JwtUtil jwtUtil;

    /**
     * LOGIN
     * - Validate credentials via User Service
     * - Generate JWT
     */
    public JwtResponse login(LoginRequest request) {

        UserInfoResponse user =
                userServiceClient.authenticate(
                        request.getEmail(),
                        request.getPassword()
                );

        String token = jwtUtil.generateToken
                (
                user.id(),       // 👈 FROM USER SERVICE
                user.email(),
                user.role()
        );

        return new JwtResponse(token);
    }


    /**
     * REGISTER NORMAL USER
     */
    public void register(RegisterUserRequest request)
    {
        userServiceClient.register(
                request.email(),
                request.password(),
                "USER"
        );
    }

    /**
     * REGISTER ADMIN (ONLY CALLED BY ADMIN CONTROLLER)
     */
    public void createAdmin(RegisterUserRequest request) {
        userServiceClient.register(
                request.email(),
                request.password(),
                "ADMIN"
        );
    }
}
