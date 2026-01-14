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

class UserAuthControllerTest {

    @InjectMocks
    private UserAuthController userAuthController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginEndpointReturnsJwtOnSuccess() {
        LoginRequest request = new LoginRequest("admin", "adminpass");
        LoginResponse response = new LoginResponse("jwt-token-admin", "admin");

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        ResponseEntity<LoginResponse> result = userAuthController.login(request);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("jwt-token-admin", result.getBody().getToken());
        assertEquals("admin", result.getBody().getUsername());
        verify(authService, times(1)).login(request);
    }

    @Test
    void testLoginEndpointThrowsExceptionOnFailure() {
        LoginRequest request = new LoginRequest("admin", "badpass");
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userAuthController.login(request);
        });

        assertTrue(exception.getMessage().contains("Authentication failed"));
        verify(authService, times(1)).login(request);
    }
}
