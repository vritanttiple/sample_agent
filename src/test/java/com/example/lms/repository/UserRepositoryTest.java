package com.example.lms.repository;

import com.example.lms.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by username")
    void findByUsername_ExistingUser_ReturnsUser() {
        User user = new User(null, "testuser", "pass");
        userRepository.save(user);
        Optional<User> result = userRepository.findByUsername("testuser");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    @DisplayName("Should return empty for non-existing username")
    void findByUsername_NonExistingUser_ReturnsEmpty() {
        Optional<User> result = userRepository.findByUsername("nouser");
        assertFalse(result.isPresent());
    }
}
