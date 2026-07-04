package com.jumpstart.food_ordering_system.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jumpstart.food_ordering_system.entity.User;
import com.jumpstart.food_ordering_system.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Read the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Rule: Expect format "Bearer <token>"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Pass it down the chain unauthenticated
            return;
        }

        jwt = authHeader.substring(7); // Extract the token text after "Bearer "

        try {
            userEmail = jwtUtils.extractEmail(jwt); // Extract email from token

            // 3. If email is valid and user isn't already authenticated in this request session
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details from the database
                User user = userRepository.findByEmail(userEmail).orElse(null);

                // 4. Validate token integrity and expiration
                if (user != null && jwtUtils.isTokenValid(jwt, user.getEmail())) {

                    // Map roles to Spring Security's SimpleGrantedAuthority objects
                    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                            .collect(Collectors.toList());

                    // Build the authentication token wrapper
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            authorities
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 5. Populate the SecurityContext so Spring Security knows who is calling
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Guardrail rule: Never throw a 500 error if token parsing fails, let the chain continue unauthenticated
            logger.error("Cannot set user authentication: {}", e);
        }

        // Continue along the filter sequence
        filterChain.doFilter(request, response);
    }
}