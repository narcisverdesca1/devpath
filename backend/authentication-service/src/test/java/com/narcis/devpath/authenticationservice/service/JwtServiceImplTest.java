package com.narcis.devpath.authenticationservice.service;

import com.narcis.devpath.authenticationservice.entity.Role;
import com.narcis.devpath.authenticationservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JwtServiceImplTest {

    private final JwtServiceImpl jwtService = new JwtServiceImpl();

    @Test
    void generateToken_shouldCreateValidToken() {
        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                "devpath-super-secret-key-devpath-super-secret-key"
        );

        ReflectionTestUtils.setField(
                jwtService,
                "expiration",
                86400000L
        );

        User user = new User();
        user.setEmail("mario.rossi@test.com");
        user.setRole(Role.USER);

        String token = jwtService.generateToken(user);

        assertThat(token).isNotBlank();
        assertThat(jwtService.isTokenValid(token)).isTrue();
        assertThat(jwtService.extractUsername(token))
                .isEqualTo("mario.rossi@test.com");
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenTokenIsInvalid() {
        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                "devpath-super-secret-key-devpath-super-secret-key"
        );

        ReflectionTestUtils.setField(
                jwtService,
                "expiration",
                86400000L
        );

        String invalidToken = "invalid.token.value";

        boolean result = jwtService.isTokenValid(invalidToken);

        assertThat(result).isFalse();
    }
}
