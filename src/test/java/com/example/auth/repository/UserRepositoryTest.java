package com.example.auth.repository;

import com.example.auth.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_shouldReturnUser_whenUserExists() {
        User user = new User(null, "testuser", "password", "ROLE_USER");
        userRepository.save(user);
        User found = userRepository.findByUsername("testuser").orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
    }

    @Test
    void findByUsername_shouldReturnEmpty_whenUserDoesNotExist() {
        assertThat(userRepository.findByUsername("nouser")).isEmpty();
    }
}
