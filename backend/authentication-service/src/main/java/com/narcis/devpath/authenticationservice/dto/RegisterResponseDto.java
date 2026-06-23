package com.narcis.devpath.authenticationservice.dto;

import com.narcis.devpath.authenticationservice.entity.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Role role,
        boolean enabled,
        LocalDateTime createdAt
) {
}