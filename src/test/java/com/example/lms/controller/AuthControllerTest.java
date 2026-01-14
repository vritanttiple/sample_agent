package com.example.lms.controller;

import com.example.lms.model.LoginRequest;
import com.example.lms.model.LoginResponse;
import com.example.lms.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");
        LoginResponse loginResponse = new LoginResponse("jwt-token", "testuser");

        when(authService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("jwt-token", response.getBody().getToken());
        assertEquals("testuser", response.getBody().getUsername());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authController.login(loginRequest);
        });
        assertTrue(exception.getMessage().contains("Invalid credentials"));
        verify(authService, times(1)).login(loginRequest);
    }
}
