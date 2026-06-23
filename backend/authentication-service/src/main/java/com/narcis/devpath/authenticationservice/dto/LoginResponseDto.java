package com.narcis.devpath.authenticationservice.dto;


public record LoginResponseDto(
        String accessToken,
        String tokenType
) {
}
