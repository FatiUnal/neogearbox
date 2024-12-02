package com.example.blogv1.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String wrongPassword) {
        super(wrongPassword);
    }
}
