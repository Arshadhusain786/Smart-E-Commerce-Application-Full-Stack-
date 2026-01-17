package com.smartcommerce.auth.util;

import com.smartcommerce.auth.client.UserServiceClient;
import com.smartcommerce.auth.dto.response.UserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        if (email == null) {
            throw new RuntimeException("Email not found from Google");
        }

        // Create or fetch user from User Service
        UserInfoResponse user =
                userServiceClient.getOrCreateOAuthUser(email, "GOOGLE");

        // Generate JWT
        String token = jwtUtil.generateToken(
                user.id(),
                user.email(),
                user.role()
        );

        // 🚀 REDIRECT TO FRONTEND WITH TOKEN
        response.sendRedirect(
                "http://localhost:3000/oauth-success?token=" + token
        );
    }
}
