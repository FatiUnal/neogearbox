package com.example.blogv1.exception;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
