package com.example.lms.service;

import com.example.lms.dto.LoginRequest;
import com.example.lms.dto.LoginResponse;
import com.example.lms.entity.User;
import com.example.lms.repository.UserRepository;
import com.example.lms.security.JWTTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTTokenProvider jwtTokenProvider;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should login with valid credentials and return JWT token")
    void login_ValidCredentials_ReturnsToken() {
        LoginRequest loginRequest = new LoginRequest("user", "password");
        User user = new User(1L, "user", "encodedpass");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", "encodedpass")).thenReturn(true);
        Mockito.when(jwtTokenProvider.generateToken(user)).thenReturn("jwt-token");

        LoginResponse response = authService.login(loginRequest);
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    @DisplayName("Should throw exception for invalid username")
    void login_InvalidUsername_ThrowsException() {
        LoginRequest loginRequest = new LoginRequest("nouser", "password");
        Mockito.when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for invalid password")
    void login_InvalidPassword_ThrowsException() {
        LoginRequest loginRequest = new LoginRequest("user", "wrongpass");
        User user = new User(1L, "user", "encodedpass");
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("wrongpass", "encodedpass")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        assertEquals("Invalid credentials", exception.getMessage());
    }
}
