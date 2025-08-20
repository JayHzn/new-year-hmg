package com.example.exception;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
    HttpStatus status,
    String message,
    Map<String, String> errors,
    LocalDateTime timestamp
) {
    public ApiError(HttpStatus status, String message) {
        this(status, message, Map.of(), LocalDateTime.now());
    }

    public ApiError(HttpStatus status, String message, Map<String, String> errors) {
        this(status, message, errors, LocalDateTime.now());
    }
}
