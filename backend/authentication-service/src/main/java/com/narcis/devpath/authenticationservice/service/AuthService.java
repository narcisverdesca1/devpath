package com.narcis.devpath.authenticationservice.service;

import com.narcis.devpath.authenticationservice.dto.LoginRequestDto;
import com.narcis.devpath.authenticationservice.dto.LoginResponseDto;
import com.narcis.devpath.authenticationservice.dto.RegisterRequestDto;
import com.narcis.devpath.authenticationservice.dto.RegisterResponseDto;

public interface AuthService {
    RegisterResponseDto register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto request);
}
