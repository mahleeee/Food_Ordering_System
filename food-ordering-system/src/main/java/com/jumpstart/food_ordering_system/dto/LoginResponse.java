package com.jumpstart.food_ordering_system.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token; // This is our short-lived access token
    private String refreshToken; // This is our long-lived refresh token
    private String email;
    private String name;
    private List<String> roles;
}