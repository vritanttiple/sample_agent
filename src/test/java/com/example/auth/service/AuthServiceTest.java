package com.example.auth.service;

import com.example.auth.model.LoginRequest;
import com.example.auth.model.LoginResponse;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("user1", "password1");
        User user = new User(1L, "user1", "password1", "ROLE_USER");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");

        LoginResponse response = authService.login(request);
        assertThat(response.getToken()).isEqualTo("jwt-token");
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        LoginRequest request = new LoginRequest("user2", "password2");
        when(userRepository.findByUsername("user2")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Invalid credentials");
    }

    @Test
    void login_shouldThrowException_whenPasswordIncorrect() {
        LoginRequest request = new LoginRequest("user1", "wrong");
        User user = new User(1L, "user1", "password1", "ROLE_USER");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Invalid credentials");
    }
}
