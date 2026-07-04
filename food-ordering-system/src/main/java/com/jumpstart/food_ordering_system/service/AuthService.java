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
import java.util.List;
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

        // 4. Generate JWT token using our utility
        String token = jwtUtils.generateToken(user.getEmail());

        // 5. Extract role names
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // 6. Return response package
        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .roles(roles)
                .build();
    }
}