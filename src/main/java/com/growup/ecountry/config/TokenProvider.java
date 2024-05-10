package com.growup.ecountry.config;

import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String secret;
}
