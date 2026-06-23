package com.narcis.devpath.authenticationservice.controller;

import com.narcis.devpath.authenticationservice.dto.LoginRequestDto;
import com.narcis.devpath.authenticationservice.dto.LoginResponseDto;
import com.narcis.devpath.authenticationservice.dto.RegisterRequestDto;
import com.narcis.devpath.authenticationservice.dto.RegisterResponseDto;
import com.narcis.devpath.authenticationservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(requestDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(requestDto));
    }


}
