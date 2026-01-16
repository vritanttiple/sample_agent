package com.example.lms.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {
    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    @DisplayName("Should provide a valid PasswordEncoder bean")
    void passwordEncoderBean_ShouldNotBeNull() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("password", encoder.encode("password")));
    }
}
