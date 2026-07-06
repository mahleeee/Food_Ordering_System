package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.config.JwtUtils;
import com.jumpstart.food_ordering_system.dto.LoginRequest;
import com.jumpstart.food_ordering_system.dto.LoginResponse;
import com.jumpstart.food_ordering_system.dto.RegisterRequest;
import com.jumpstart.food_ordering_system.entity.Role;
import com.jumpstart.food_ordering_system.entity.User;
import com.jumpstart.food_ordering_system.repository.RoleRepository;
import com.jumpstart.food_ordering_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils; // Inject our new JWT utility

    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Default customer role not found in database"));

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .active(true)
                .roles(Collections.singletonList(customerRole))
                .build();

        userRepository.save(newUser);
        return "User registered successfully";
    }
    public LoginResponse loginUser(LoginRequest request) {
        // 1. Find user by email. If missing, return generic error message for security
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // 2. Verify password match
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials"); // Same message as above!
        }

        // 3. Rule: Check if account is inactive
        if (!user.isActive()) {
            throw new RuntimeException("Account inactive. Please contact support.");
        }

        // 4. Generate BOTH short-lived Access and long-lived Refresh tokens using our utility
        String accessToken = jwtUtils.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        // 5. Extract role names
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // 6. Return response package with both tokens included
        return LoginResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getName())
                .roles(roles)
                .build();
    }

    // --- EXTRA CREDIT SERVICE IMPLEMENTATIONS ---

    public Map<String, Object> getUserProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        return convertToProfileMap(user);
    }

    public Map<String, Object> updateUserProfile(String email, Map<String, Object> updates) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        // Update ONLY permitted fields (Ignore email/password modifications if passed)
        if (updates.containsKey("name")) {
            user.setName((String) updates.get("name"));
        }
        if (updates.containsKey("phoneNumber")) {
            user.setPhoneNumber((String) updates.get("phoneNumber"));
        }
        if (updates.containsKey("address")) {
            user.setAddress((String) updates.get("address"));
        }

        User updatedUser = userRepository.save(user);
        return convertToProfileMap(updatedUser);
    }

    // Helper method to keep code DRY and hide password data
    private Map<String, Object> convertToProfileMap(User user) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("name", user.getName());
        profile.put("email", user.getEmail());
        profile.put("phoneNumber", user.getPhoneNumber());
        profile.put("address", user.getAddress());
        profile.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return profile;
    }
    public Map<String, String> refreshAccessToken(String refreshToken) {
        // 1. Verify the refresh token is not expired/tampered with
        if (jwtUtils.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token is expired or invalid. Please log in again.");
        }

        // 2. Extract user email from the refresh token
        String email = jwtUtils.extractEmail(refreshToken);

        // 3. Ensure user exists and is still active in system
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("Account is inactive.");
        }

        // 4. Issue a shiny new 15-minute access token
        String newAccessToken = jwtUtils.generateAccessToken(email);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", newAccessToken);
        tokens.put("refreshToken", refreshToken); // Keep using the same 7-day refresh token
        return tokens;
    }
}