package com.narcis.devpath.authenticationservice.service;

import com.narcis.devpath.authenticationservice.dto.LoginRequestDto;
import com.narcis.devpath.authenticationservice.dto.LoginResponseDto;
import com.narcis.devpath.authenticationservice.dto.RegisterRequestDto;
import com.narcis.devpath.authenticationservice.dto.RegisterResponseDto;
import com.narcis.devpath.authenticationservice.entity.Role;
import com.narcis.devpath.authenticationservice.entity.User;
import com.narcis.devpath.authenticationservice.mapper.UserMapper;
import com.narcis.devpath.authenticationservice.repository.UserRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {
        boolean emailExist = userRepository.existsByEmail(request.email());
        if (emailExist) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEnabled(true);

        return userMapper.toRegisterResponseDto(userRepository.save(user));
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        return null;
    }
}
