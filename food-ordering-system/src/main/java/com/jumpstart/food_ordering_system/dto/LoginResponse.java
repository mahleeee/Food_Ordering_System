package com.jumpstart.food_ordering_system.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String email;
    private String name;
    private List<String> roles;
}