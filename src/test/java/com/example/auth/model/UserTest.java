package com.example.auth.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class UserTest {
    @Test
    void user_shouldHaveValidFields() {
        User user = new User(1L, "testuser", "password", "ROLE_USER");
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
    }

    @Test
    void user_equalsAndHashCode_shouldWork() {
        User user1 = new User(1L, "testuser", "password", "ROLE_USER");
        User user2 = new User(1L, "testuser", "password", "ROLE_USER");
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    void user_notEqual_whenDifferentFields() {
        User user1 = new User(1L, "testuser", "password", "ROLE_USER");
        User user2 = new User(2L, "otheruser", "password", "ROLE_ADMIN");
        assertThat(user1).isNotEqualTo(user2);
    }
}
