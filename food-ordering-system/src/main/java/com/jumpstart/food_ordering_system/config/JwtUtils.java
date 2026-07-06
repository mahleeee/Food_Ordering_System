package com.jumpstart.food_ordering_system.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secretKey;

    // 1. Generate a short-lived Access Token (15 Minutes)
    public String generateAccessToken(String email) {
        long fifteenMinutesInMillis = 15 * 60 * 1000;
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + fifteenMinutesInMillis))
                .signWith(getSigningKey())
                .compact();
    }

    // 2. Generate a long-lived Refresh Token (7 Days)
    public String generateRefreshToken(String email) {
        long sevenDaysInMillis = 7L * 24 * 60 * 60 * 1000;
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + sevenDaysInMillis))
                .signWith(getSigningKey())
                .compact();
    }

    // Extract the email (subject) from a token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Validate that a token is not expired and has not been tampered with
    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // Helper to validate a token format/expiration stand-alone
    public boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        } catch (Exception e) {
            return true; // If parsing fails or is tampered with, treat as expired/invalid
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}