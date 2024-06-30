package com.groupee.front.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    @NotNull
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtBuilder jwtBuilder() {
        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts
                .builder()
                .signWith(secretKey, SignatureAlgorithm.HS256);
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts
                .parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build();
    }
}
