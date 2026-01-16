package com.example.lms.security;

import com.example.lms.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class JWTTokenProviderTest {
    private JWTTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // Using a test secret
        jwtTokenProvider = new JWTTokenProvider("test-secret", 3600000);
    }

    @Test
    @DisplayName("Should generate valid JWT token for user")
    void generateToken_ValidUser_ReturnsToken() {
        User user = new User(1L, "user", "encodedpass");
        String token = jwtTokenProvider.generateToken(user);
        assertNotNull(token);
        assertTrue(token.split(".").length >= 2);
    }

    @Test
    @DisplayName("Should throw exception for null user")
    void generateToken_NullUser_ThrowsException() {
        assertThrows(NullPointerException.class, () -> jwtTokenProvider.generateToken(null));
    }
}
