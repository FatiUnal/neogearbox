package com.example.blogv1.Config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationConstant {


    @Value("${jwt.secret}")
    public static String jwtSecret;

    @Value("${jwt.header}")
    public static String jwtHeader;

    public static String getJwtSecret() {
        return jwtSecret;
    }

    public static String getJwtHeader() {
        return jwtHeader;
    }
}
