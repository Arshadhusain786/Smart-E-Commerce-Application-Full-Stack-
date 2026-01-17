package com.smartcommerce.auth.controller;

import com.smartcommerce.auth.dto.request.LoginRequest;
import com.smartcommerce.auth.dto.request.RegisterUserRequest;
import com.smartcommerce.auth.dto.response.JwtResponse;
import com.smartcommerce.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    /**
     * ADMIN creates another admin
     * Role decision is done in USER SERVICE
     */
    @PostMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAdmin(
            @RequestBody @Valid RegisterUserRequest request) {

        authService.createAdmin(request);
        return ResponseEntity.ok("Admin created successfully");
    }

    /**
     * Login API
     */
    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Register API (default USER)
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterUserRequest request) {

        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
