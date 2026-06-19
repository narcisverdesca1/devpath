package com.narcis.devpath.learningservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Course response")
public record CourseResponseDto(

        @Schema(
                description = "Course identifier",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Course title",
                example = "Spring Boot Fundamentals"
        )
        String title,

        @Schema(
                description = "Course description",
                example = "Learn Spring Boot from beginner to advanced level"
        )
        String description,

        @Schema(
                description = "Course difficulty",
                example = "BEGINNER"
        )
        String difficulty,

        @Schema(
                description = "Course creation timestamp",
                example = "2026-06-19T10:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Course last update timestamp",
                example = "2026-06-19T15:45:00"
        )
        LocalDateTime updatedAt

) {
}