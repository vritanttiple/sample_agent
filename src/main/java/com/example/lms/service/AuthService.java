package com.example.lms.service;

import com.example.lms.model.LoginRequest;
import com.example.lms.model.LoginResponse;
import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import com.example.lms.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            User user = userRepository.findByUsername(loginRequest.getUsername());
            String token = jwtTokenProvider.generateToken(authentication);
            return new LoginResponse(token, "Login successful");
        } catch (AuthenticationException ex) {
            return new LoginResponse(null, "Invalid username or password");
        }
    }
}
