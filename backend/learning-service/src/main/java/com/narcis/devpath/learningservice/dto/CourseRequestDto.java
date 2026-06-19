package com.narcis.devpath.learningservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating or updating a course")
public record CourseRequestDto(

        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        @Schema(
                description = "Course title",
                example = "Spring Boot Fundamentals"
        )
        String title,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        @Schema(
                description = "Course description",
                example = "Learn Spring Boot from beginner to advanced level"
        )
        String description,

        @NotBlank(message = "Difficulty is required")
        @Size(max = 50, message = "Difficulty cannot exceed 50 characters")
        @Schema(
                description = "Course difficulty",
                example = "BEGINNER"
        )
        String difficulty
) {
}
