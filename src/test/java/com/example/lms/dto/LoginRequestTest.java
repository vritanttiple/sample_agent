package com.example.lms.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {
    @Test
    @DisplayName("Should create LoginRequest with valid values")
    void create_ValidValues() {
        LoginRequest request = new LoginRequest("user", "pass");
        assertEquals("user", request.getUsername());
        assertEquals("pass", request.getPassword());
    }

    @Test
    @DisplayName("Should handle null username and password")
    void create_NullValues() {
        LoginRequest request = new LoginRequest(null, null);
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }

    @Test
    @DisplayName("Should handle empty username and password")
    void create_EmptyValues() {
        LoginRequest request = new LoginRequest("", "");
        assertEquals("", request.getUsername());
        assertEquals("", request.getPassword());
    }
}
