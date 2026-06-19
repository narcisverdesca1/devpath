package com.narcis.devpath.learningservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating or updating a module")
public record ModuleRequestDto(

        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        @Schema(
                description = "Module title",
                example = "Introduction to Spring Boot"
        )
        String title,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        @Schema(
                description = "Module description",
                example = "Overview of Spring Boot fundamentals and project structure"
        )
        String description,

        @NotNull(message = "Position is required")
        @PositiveOrZero(message = "Position must be zero or positive")
        @Schema(
                description = "Module position inside the course",
                example = "1"
        )
        Integer position
) {
}