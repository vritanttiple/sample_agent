package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String email, String password, String name) throws IllegalArgumentException {
        validateInput(email, password, name);
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(email, hashedPassword, name);
        return userRepository.save(user);
    }

    private void validateInput(String email, String password, String name) {
        if (!StringUtils.hasText(email) || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!StringUtils.hasText(password) || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name is required");
        }
    }
}
