package com.smartcommerce.auth.client;

import com.smartcommerce.auth.dto.request.AuthRequest;
import com.smartcommerce.auth.dto.request.OAuthUserRequest;
import com.smartcommerce.auth.dto.request.RegisterUserRequest;
import com.smartcommerce.auth.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    /**
     * 🔐 REGISTER USER (EMAIL + PASSWORD)
     */
    public void register(String email, String password, String role) {
        restTemplate.postForLocation(
                userServiceUrl + "/internal/users/register",
                new RegisterUserRequest(
                        email,
                        password,
                        role,
                        "LOCAL"
                )
        );
    }

    /**
     * 🔐 AUTHENTICATE USER
     */
    public UserInfoResponse authenticate(String email, String password) {
        return restTemplate.postForObject(
                userServiceUrl + "/internal/users/authenticate",
                new AuthRequest(email, password),
                UserInfoResponse.class
        );
    }

    /**
     * 🔐 GOOGLE / OAUTH LOGIN
     */
    public UserInfoResponse getOrCreateOAuthUser(String email, String provider) {
        return restTemplate.postForObject(
                userServiceUrl + "/internal/users/oauth",
                new OAuthUserRequest(email, provider),
                UserInfoResponse.class
        );
    }
}
