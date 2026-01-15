package com.example.auth.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class LoginRequestTest {
    @Test
    void loginRequest_shouldHaveValidFields() {
        LoginRequest req = new LoginRequest("user1", "pass1");
        assertThat(req.getUsername()).isEqualTo("user1");
        assertThat(req.getPassword()).isEqualTo("pass1");
    }

    @Test
    void loginRequest_equalsAndHashCode_shouldWork() {
        LoginRequest r1 = new LoginRequest("user1", "pass1");
        LoginRequest r2 = new LoginRequest("user1", "pass1");
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    void loginRequest_notEqual_whenDifferentFields() {
        LoginRequest r1 = new LoginRequest("user1", "pass1");
        LoginRequest r2 = new LoginRequest("user2", "pass2");
        assertThat(r1).isNotEqualTo(r2);
    }
}
