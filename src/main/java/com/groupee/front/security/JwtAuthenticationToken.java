package com.groupee.front.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String jwt;
    private Object principal;
    private Object credentials;

    public JwtAuthenticationToken(String jwt) {
        super(null);
        this.jwt = jwt;
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        this.principal = principal;
        this.credentials = credentials;

        setAuthenticated(true);
    }
}
