package com.example.lms.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("Should create User with valid values")
    void create_ValidValues() {
        User user = new User(1L, "user", "encodedpass");
        assertEquals(1L, user.getId());
        assertEquals("user", user.getUsername());
        assertEquals("encodedpass", user.getPassword());
    }

    @Test
    @DisplayName("Should handle null values")
    void create_NullValues() {
        User user = new User(null, null, null);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    @DisplayName("Should handle empty username and password")
    void create_EmptyValues() {
        User user = new User(2L, "", "");
        assertEquals(2L, user.getId());
        assertEquals("", user.getUsername());
        assertEquals("", user.getPassword());
    }
}
