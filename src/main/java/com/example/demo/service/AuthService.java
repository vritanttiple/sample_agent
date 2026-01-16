package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        String token = jwtProvider.generateToken(user);
        return new LoginResponse(token);
    }
}
