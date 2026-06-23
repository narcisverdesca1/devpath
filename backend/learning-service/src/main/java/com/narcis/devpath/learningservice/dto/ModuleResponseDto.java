package com.narcis.devpath.learningservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Module response")
public record ModuleResponseDto(

        @Schema(
                description = "Module identifier",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Module title",
                example = "Introduction to Spring Boot"
        )
        String title,

        @Schema(
                description = "Module description",
                example = "Overview of Spring Boot fundamentals and project structure"
        )
        String description,

        @Schema(
                description = "Module position inside the course",
                example = "1"
        )
        Integer position

) {
}