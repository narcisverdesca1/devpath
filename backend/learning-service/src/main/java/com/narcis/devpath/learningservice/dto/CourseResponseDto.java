package com.narcis.devpath.learningservice.dto;

import java.time.LocalDateTime;

public record CourseResponseDto(
        Long id,
        String title,
        String description,
        String difficulty,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
