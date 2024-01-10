package com.kood.homework.translationapi.exeption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kood.homework.translationapi.model.ApiResponse;
import com.kood.homework.translationapi.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${api.version}")
    private String apiVersion;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        String message = "Method not allowed. Supported methods: " +
                String.join(", ", ex.getSupportedMethods());

        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, message, null, apiVersion).toJson(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
}
