package com.narcis.devpath.learningservice.dto;

import com.narcis.devpath.learningservice.entity.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ModuleRequestDto(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        @NotNull(message = "Position is required")
        @PositiveOrZero(message = "Position must be zero or positive")
        Integer position,

        Course course
) {
}
