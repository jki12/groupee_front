package com.groupee.front.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class JwtGenerator {
    private static final Long ACCESS_TOKEN_VALID_SECOND = TimeUnit.MINUTES.toSeconds(10);

    private final JwtBuilder jwtBuilder;

    public String create(Claims claims) {
        Date now = new Date();

        return jwtBuilder
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_SECOND))
                .setClaims(claims)
                .compact();
    }
}
