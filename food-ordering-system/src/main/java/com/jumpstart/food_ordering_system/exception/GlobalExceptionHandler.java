package com.jumpstart.food_ordering_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Validation Failures (e.g., missing name, bad email, short password -> 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("status", "error");

        // Collect all field validation failure messages
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        response.put("message", errorMessage);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 2. Handle Access Denied (Token valid but wrong role e.g., CUSTOMER hitting ADMIN route -> 403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.FORBIDDEN.value());
        response.put("status", "error");
        response.put("message", "Access denied: You do not have permission to access this resource.");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // 3. Handle Authentication Failure (Bad or missing tokens -> 401)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        response.put("status", "error");
        response.put("message", "Authentication failed: Invalid or missing token.");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 4. Handle Custom Business/Bad Request Runtime Exceptions (e.g., "Email already registered" -> 400)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();

        // Match the requirement: "Invalid credentials" or "Account inactive" should return 400
        HttpStatus status = HttpStatus.BAD_REQUEST;

        response.put("statusCode", status.value());
        response.put("status", "error");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(response);
    }

    // 5. Fallback for any unhandled generic server exceptions -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("status", "error");
        response.put("message", "An unexpected error occurred: " + ex.getMessage());
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}