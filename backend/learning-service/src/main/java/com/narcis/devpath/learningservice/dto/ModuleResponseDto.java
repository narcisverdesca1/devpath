package com.narcis.devpath.learningservice.dto;

public record ModuleResponseDto(
        Long id,
        String title,
        String description,
        Integer position
) {
}
