package com.narcis.devpath.noteservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoteResponseDto(
    Long id,
    String title,
    String description,
    Long moduleId,
    String moduleTitleSnapshot,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
