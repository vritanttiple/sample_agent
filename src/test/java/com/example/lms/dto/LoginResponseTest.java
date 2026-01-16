package com.example.lms.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {
    @Test
    @DisplayName("Should create LoginResponse with valid token")
    void create_ValidToken() {
        LoginResponse response = new LoginResponse("jwt-token");
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    @DisplayName("Should handle null token")
    void create_NullToken() {
        LoginResponse response = new LoginResponse(null);
        assertNull(response.getToken());
    }

    @Test
    @DisplayName("Should handle empty token")
    void create_EmptyToken() {
        LoginResponse response = new LoginResponse("");
        assertEquals("", response.getToken());
    }
}
