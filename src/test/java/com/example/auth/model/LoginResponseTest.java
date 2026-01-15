package com.example.auth.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class LoginResponseTest {
    @Test
    void loginResponse_shouldHaveValidToken() {
        LoginResponse resp = new LoginResponse("jwt-token");
        assertThat(resp.getToken()).isEqualTo("jwt-token");
    }

    @Test
    void loginResponse_equalsAndHashCode_shouldWork() {
        LoginResponse r1 = new LoginResponse("jwt-token");
        LoginResponse r2 = new LoginResponse("jwt-token");
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    void loginResponse_notEqual_whenDifferentTokens() {
        LoginResponse r1 = new LoginResponse("jwt-token");
        LoginResponse r2 = new LoginResponse("other-token");
        assertThat(r1).isNotEqualTo(r2);
    }
}
