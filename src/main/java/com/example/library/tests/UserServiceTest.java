package com.example.library.tests;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User(null, "newuser", "password");
        User savedUser = new User(1L, "newuser", "$2a$10$hashed");
        when(passwordEncoder.encode("password")).thenReturn("$2a$10$hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(user);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newuser", result.getUsername());
        assertEquals("$2a$10$hashed", result.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_NullUser() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(null));
        assertEquals("User cannot be null", exception.getMessage());
        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmptyPassword() {
        User user = new User(null, "user", "");
        when(passwordEncoder.encode("")).thenReturn("");
        when(userRepository.save(any(User.class))).thenReturn(new User(2L, "user", ""));

        User result = userService.registerUser(user);
        assertEquals("", result.getPassword());
        verify(passwordEncoder, times(1)).encode("");
        verify(userRepository, times(1)).save(any(User.class));
    }
}
