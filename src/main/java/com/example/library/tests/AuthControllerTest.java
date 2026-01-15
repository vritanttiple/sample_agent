package com.example.library.tests;

import com.example.demo.controller.AuthController;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest("user", "password");
        LoginResponse expectedResponse = new LoginResponse("jwt-token", "user");
        when(authService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<LoginResponse> response = authController.login(request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(authService, times(1)).login(request);
    }

    @Test
    void testLogin_Failure() {
        LoginRequest request = new LoginRequest("user", "wrong-password");
        when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        Exception exception = assertThrows(RuntimeException.class, () -> authController.login(request));
        assertEquals("Invalid credentials", exception.getMessage());
        verify(authService, times(1)).login(request);
    }
}
