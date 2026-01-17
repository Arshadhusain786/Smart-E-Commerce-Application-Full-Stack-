package com.smartcommerce.user_service.controller;

import com.smartcommerce.user_service.dto.request.AuthenticateUserRequest;
import com.smartcommerce.user_service.dto.request.OAuthUserRequest;
import com.smartcommerce.user_service.dto.request.RegisterUserRequest;
import com.smartcommerce.user_service.dto.response.AuthenticateUserResponse;
import com.smartcommerce.user_service.dto.response.UserInfoResponse;
import com.smartcommerce.user_service.entity.User;
import com.smartcommerce.user_service.enums.AuthProvider;
import com.smartcommerce.user_service.enums.Role;
import com.smartcommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor


public class InternalUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public void register(@RequestBody RegisterUserRequest request) {

        userRepository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });

        User user = new User();
        user.setEmail(request.email());

        // ✅ SERVICE-OWNED VALUES (FIX)
        user.setRole(Role.USER);
        user.setProvider(AuthProvider.LOCAL);
        user.setEnabled(true);

        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
        System.out.println("SAVED USER ID = " + user.getId());
    }
    @PostMapping("/register-admin")
    public void registerAdmin(@RequestBody RegisterUserRequest request) {

        System.out.println("ADMIN REGISTER HIT");
        User user = new User();
        user.setEmail(request.email());
        user.setRole(Role.ADMIN);   // 🔥 ADMIN HERE
        user.setProvider(AuthProvider.LOCAL);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
        System.out.println("SAVED USER ID = " + user.getId());
    }

    @PostMapping("/authenticate")
    public UserInfoResponse authenticate(
            @RequestBody AuthenticateUserRequest request
    ) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("User disabled");
        }

        if (user.getProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException("Use Google login");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ FIXED RESPONSE
        return new UserInfoResponse(
                user.getId(),          // ✅ USER ID (CRITICAL)
                user.getEmail(),
                user.getRole(),
                true
        );
    }


    @PostMapping("/oauth")
    public UserInfoResponse oauth(@RequestBody OAuthUserRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(request.email());
                    u.setRole(Role.USER);
                    u.setProvider(AuthProvider.valueOf(request.provider()));
                    u.setEnabled(true);
                    u.setPassword(null);
                    return userRepository.save(u);
                });

        return new UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                true
        );
    }
    @GetMapping("/debug/all")
    public List<User> all() {
        return userRepository.findAll();
    }

}
