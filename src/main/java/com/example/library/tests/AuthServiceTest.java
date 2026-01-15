package com.example.library.tests;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest("user", "password");
        User user = new User(1L, "user", "$2a$10$hash");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(user)).thenReturn("jwt-token");

        LoginResponse response = authService.login(request);
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("user", response.getUsername());
        verify(userRepository, times(1)).findByUsername("user");
        verify(passwordEncoder, times(1)).matches("password", user.getPassword());
        verify(jwtProvider, times(1)).generateToken(user);
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequest request = new LoginRequest("user", "password");
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("user");
        verify(passwordEncoder, times(0)).matches(anyString(), anyString());
        verify(jwtProvider, times(0)).generateToken(any());
    }

    @Test
    void testLogin_InvalidPassword() {
        LoginRequest request = new LoginRequest("user", "wrong-password");
        User user = new User(1L, "user", "$2a$10$hash");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("Invalid credentials", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("user");
        verify(passwordEncoder, times(1)).matches("wrong-password", user.getPassword());
        verify(jwtProvider, times(0)).generateToken(any());
    }
}
