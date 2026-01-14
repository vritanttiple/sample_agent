package com.example.lms.service;

import com.example.lms.model.LoginRequest;
import com.example.lms.model.LoginResponse;
import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import com.example.lms.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginWithValidCredentialsReturnsJwtToken() {
        LoginRequest request = new LoginRequest("user1", "pass1");
        User user = new User(1L, "user1", "pass1", "ROLE_USER");

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateToken(user)).thenReturn("jwt-user1");

        LoginResponse response = authService.login(request);

        assertEquals("jwt-user1", response.getToken());
        assertEquals("user1", response.getUsername());
        verify(userRepository, times(1)).findByUsername("user1");
        verify(jwtTokenProvider, times(1)).generateToken(user);
    }

    @Test
    void testLoginWithInvalidUsernameThrowsException() {
        LoginRequest request = new LoginRequest("nouser", "nopass");

        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertTrue(exception.getMessage().contains("Invalid username or password"));
        verify(userRepository, times(1)).findByUsername("nouser");
        verify(jwtTokenProvider, never()).generateToken(any());
    }

    @Test
    void testLoginWithWrongPasswordThrowsException() {
        LoginRequest request = new LoginRequest("user2", "wrongpass");
        User user = new User(2L, "user2", "correctpass", "ROLE_USER");

        when(userRepository.findByUsername("user2")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertTrue(exception.getMessage().contains("Invalid username or password"));
        verify(userRepository, times(1)).findByUsername("user2");
        verify(jwtTokenProvider, never()).generateToken(any());
    }
}
