package com.example.lms.service;

import com.example.lms.repository.UserRepository;
import com.example.lms.security.JWTProvider;
import com.example.lms.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.lms.model.User;
import org.springframework.security.authentication.BadCredentialsException;

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

    public LoginResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        String token = jwtProvider.generateToken(user);
        return new LoginResponse(token);
    }
}