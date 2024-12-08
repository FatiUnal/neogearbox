package com.example.blogv1.Config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationConstant {


    public static String jwtSecret = "C2VB987vxNVXvn543xVvxmVX7BCVM432NxvbvbnVXncm";
    public static String jwtHeader = "Authorization";

    public static String getJwtSecret() {
        return jwtSecret;
    }

    public static String getJwtHeader() {
        return jwtHeader;
    }
}
