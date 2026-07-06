package com.jumpstart.food_ordering_system;

import com.jumpstart.food_ordering_system.config.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    // A secure 256-bit base64 encoded dummy secret key for testing purposes
    private final String testSecret = "YTM0NTVnN2g4ajJrM2w0bTVuNnA3cThzOXQwYTFzMmQzZjRnNWg2ajdrOGw5bTBhMXMyZDNmNGc1aDZqN2s4bTluMA==";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        // Inject the test secret key into the private @Value field
        ReflectionTestUtils.setField(jwtUtils, "secretKey", testSecret);
    }

    @Test
    void testGenerateAndExtractAccessToken() {
        String email = "testuser@example.com";

        String token = jwtUtils.generateAccessToken(email);

        assertNotNull(token);
        assertEquals(email, jwtUtils.extractEmail(token));
    }

    @Test
    void testGenerateAndExtractRefreshToken() {
        String email = "testuser@example.com";

        String token = jwtUtils.generateRefreshToken(email);

        assertNotNull(token);
        assertEquals(email, jwtUtils.extractEmail(token));
    }

    @Test
    void testIsTokenValid_Success() {
        String email = "testuser@example.com";
        String token = jwtUtils.generateAccessToken(email);

        assertTrue(jwtUtils.isTokenValid(token, email));
    }

    @Test
    void testIsTokenValid_WrongEmail() {
        String email = "testuser@example.com";
        String token = jwtUtils.generateAccessToken(email);

        assertFalse(jwtUtils.isTokenValid(token, "wrong@example.com"));
    }

    @Test
    void testIsTokenExpired_WithTamperedToken() {
        // A random gibberish token string that mimics a tampered signature
        String tamperedToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0In0.fakeSignatureHere";

        // Should catch the parsing exception under the hood and return true (treating it as invalid/expired)
        assertTrue(jwtUtils.isTokenExpired(tamperedToken));
    }
}