package com.example.blogv1.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUnAuthorizedException(UnAuthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


}
