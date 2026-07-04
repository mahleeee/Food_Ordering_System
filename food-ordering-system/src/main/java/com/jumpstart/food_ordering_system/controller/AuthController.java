package com.jumpstart.food_ordering_system.controller;

import com.jumpstart.food_ordering_system.dto.LoginRequest;
import com.jumpstart.food_ordering_system.dto.LoginResponse;
import com.jumpstart.food_ordering_system.dto.RegisterRequest;
import com.jumpstart.food_ordering_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        String message = authService.registerUser(request);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginUser(request);
        return ResponseEntity.ok(loginResponse);
    }
}