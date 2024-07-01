package com.groupee.front.jwt;

import com.groupee.front.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = { "jwt.secret=2442b7d2f48ebe1c5af8454a8ef1e22d" }, classes = { JwtConfig.class, JwtGenerator.class })
class JwtGeneratorTest {
    @Mock
    JwtBuilder jwtBuilder;
    @InjectMocks
    JwtGenerator jwtGenerator;
    String[] authorities = { "USER" };

    @BeforeEach
    void setUp() {
        when(jwtBuilder.setIssuedAt(any(Date.class))).thenReturn(jwtBuilder);
        when(jwtBuilder.setExpiration(any(Date.class))).thenReturn(jwtBuilder);
        when(jwtBuilder.setClaims(any(Claims.class))).thenReturn(jwtBuilder);
        when(jwtBuilder.compact()).thenReturn("jwt");
    }

    @DisplayName("jwt 생성시 반드시 호출되어야 할 함수들이 잘 호출되었는지 테스트")
    @Test
    void create() {
        Claims claims = new DefaultClaims(Map.of("id", 1, "authorities", authorities));

        jwtGenerator.create(claims);

        verify(jwtBuilder).setExpiration(any(Date.class));
        verify(jwtBuilder).setIssuedAt(any(Date.class));
        verify(jwtBuilder).setClaims(any(Claims.class));
    }
}