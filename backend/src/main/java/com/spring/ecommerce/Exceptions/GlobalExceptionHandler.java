package com.spring.ecommerce.Exceptions;

import com.spring.ecommerce.DTOs.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (Exception.class)
    public ResponseEntity<Response> handleException(Exception e, WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<Response> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (InvalidCredentialsException.class)
    public ResponseEntity<Response> handleInvalidCredentialsException(InvalidCredentialsException e
            , WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
