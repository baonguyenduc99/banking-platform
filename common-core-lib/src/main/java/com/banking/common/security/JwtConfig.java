package com.banking.common.security;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.banking.common.security.jwt.DefaultJwtValidator;
import com.banking.common.security.jwt.JwtValidator;

@Configuration
public class JwtConfig {

    @Bean
    JwtValidator jwtValidator(@Value("${jwt.secret}") String secret) {
        return new DefaultJwtValidator(secret);
    }
}
