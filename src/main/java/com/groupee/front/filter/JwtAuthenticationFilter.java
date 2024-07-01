package com.groupee.front.filter;

import com.groupee.front.security.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_KEY = "Authorization";

    private final AuthenticationManager authenticationManager;

    private String findAndDecodeToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(AUTHORIZATION_KEY))
                .findFirst()
                .orElse(null);

        return (cookie != null) ? new String(Base64Utils.decodeFromString(cookie.getValue())) : null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = findAndDecodeToken(request);

        if (jwt != null) {
            try {
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext(); // 다음 필터로 넘어가면 error가 발생하도록 context를 비워둔다.
            }
        }

        filterChain.doFilter(request, response);
    }
}
