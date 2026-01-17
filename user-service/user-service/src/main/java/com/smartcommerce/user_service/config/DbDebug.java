package com.smartcommerce.user_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbDebug {

    @Value("${spring.datasource.url}")
    private String url;

    @PostConstruct
    public void printDb() {
        System.out.println(">>> USER SERVICE DB URL = " + url);
    }
}