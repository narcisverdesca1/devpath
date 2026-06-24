package com.narcis.devpath.authenticationservice.service;

import com.narcis.devpath.authenticationservice.dto.LoginRequestDto;
import com.narcis.devpath.authenticationservice.dto.LoginResponseDto;
import com.narcis.devpath.authenticationservice.entity.Role;
import com.narcis.devpath.authenticationservice.entity.User;
import com.narcis.devpath.authenticationservice.exception.InvalidCredentialsException;
import com.narcis.devpath.authenticationservice.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        LoginRequestDto request = new LoginRequestDto(
                "mario.rossi@test.com",
                "password123"
        );

        User user = new User();
        user.setEmail("mario.rossi@test.com");
        user.setPassword("hashedPassword");
        user.setRole(Role.USER);
        user.setEnabled(true);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(userDetails);

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        LoginResponseDto result = authService.login(request);

        assertThat(result).isNotNull();
        assertThat(result.token()).isEqualTo("jwt-token");
    }

    @Test
    void login_shouldThrowInvalidCredentialsException_whenCredentialsAreInvalid() {

        LoginRequestDto request = new LoginRequestDto(
                "mario.rossi@test.com",
                "wrong-password"
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class);
    }
}
