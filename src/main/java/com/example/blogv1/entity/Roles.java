package com.example.blogv1.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN");

    private final String authority;

    Roles(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
