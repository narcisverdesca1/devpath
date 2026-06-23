package com.narcis.devpath.authenticationservice.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(
        String token
) {
}
