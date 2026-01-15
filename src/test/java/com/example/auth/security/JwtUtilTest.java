package com.example.auth.security;

import com.example.auth.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;

class JwtUtilTest {
    private JwtUtil jwtUtil;
    private final String secret = "testsecret";
    private final long expirationMs = 3600000L;

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil(secret, expirationMs);
    }

    @Test
    void generateToken_shouldReturnValidJwt_forUser() {
        User user = new User(1L, "user1", "password", "ROLE_USER");
        String token = jwtUtil.generateToken(user);
        assertThat(token).isNotBlank();
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        assertThat(claims.getSubject()).isEqualTo("user1");
        assertThat(claims.get("role")).isEqualTo("ROLE_USER");
    }

    @Test
    void validateToken_shouldReturnTrue_forValidToken() {
        User user = new User(1L, "user1", "password", "ROLE_USER");
        String token = jwtUtil.generateToken(user);
        boolean valid = jwtUtil.validateToken(token, user);
        assertThat(valid).isTrue();
    }

    @Test
    void validateToken_shouldReturnFalse_forInvalidUser() {
        User user = new User(1L, "user1", "password", "ROLE_USER");
        User other = new User(2L, "other", "password", "ROLE_USER");
        String token = jwtUtil.generateToken(user);
        boolean valid = jwtUtil.validateToken(token, other);
        assertThat(valid).isFalse();
    }

    @Test
    void validateToken_shouldReturnFalse_forExpiredToken() {
        User user = new User(1L, "user1", "password", "ROLE_USER");
        String token = Jwts.builder()
            .setSubject("user1")
            .claim("role", "ROLE_USER")
            .setExpiration(new Date(System.currentTimeMillis() - 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
        boolean valid = jwtUtil.validateToken(token, user);
        assertThat(valid).isFalse();
    }
}
