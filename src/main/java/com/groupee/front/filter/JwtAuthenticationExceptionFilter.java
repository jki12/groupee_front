package com.groupee.front.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());

            Cookie cookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("Authorization"))
                    .findFirst()
                    .get();

            Assert.notNull(cookie, "Authorization 쿠키는 반드시 존재해야 합니다.");

            cookie.setMaxAge(0);
            cookie.setPath("/");

            response.addCookie(cookie);

            response.sendRedirect("/login");
        }
    }
}
