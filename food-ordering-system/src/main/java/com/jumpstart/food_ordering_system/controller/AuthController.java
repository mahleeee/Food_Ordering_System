package com.jumpstart.food_ordering_system.controller;

import com.jumpstart.food_ordering_system.dto.LoginRequest;
import com.jumpstart.food_ordering_system.dto.LoginResponse;
import com.jumpstart.food_ordering_system.dto.RegisterRequest;
import com.jumpstart.food_ordering_system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration, login, token refresh, and profile management")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with a hashed password.")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        String message = authService.registerUser(request);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Validates credentials and returns an access token along with a refresh token.")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginUser(request);
        return ResponseEntity.ok(loginResponse);
    }

    // --- EXTRA CREDIT ENDPOINTS ADDED TO THE BOTTOM ---

    @GetMapping("/me")
    @Operation(summary = "Get current user profile", description = "Retrieves the authenticated user's details using the JWT access token.")
    public ResponseEntity<?> getCurrentUserProfile() {
        // 1. Read the email from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // 2. Fetch the clean profile data via authService
        Object userProfile = authService.getUserProfileByEmail(email);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user profile", description = "Updates the authenticated user's details in the database.")
    public ResponseEntity<?> updateUserProfile(@RequestBody Map<String, Object> updateRequest) {
        // 1. Read the email from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // 2. Pass the updates to authService to save to MySQL
        Object updatedProfile = authService.updateUserProfile(email, updateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Accepts a valid refresh token and returns a newly generated short-lived access token.")
    public ResponseEntity<?> refreshMyToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Refresh token is required"));
        }

        Map<String, String> newTokens = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(newTokens);
    }
}