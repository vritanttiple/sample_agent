package com.example.auth.controller;

import com.example.auth.model.LoginRequest;
import com.example.auth.model.LoginResponse;
import com.example.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void login_shouldReturnJwtToken_whenCredentialsAreValid() throws Exception {
        LoginRequest request = new LoginRequest("user1", "password1");
        LoginResponse response = new LoginResponse("jwt-token");
        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType("application/json")
                .content("{\"username\":\"user1\",\"password\":\"password1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void login_shouldReturnUnauthorized_whenCredentialsAreInvalid() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType("application/json")
                .content("{\"username\":\"invalid\",\"password\":\"wrong\"}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
