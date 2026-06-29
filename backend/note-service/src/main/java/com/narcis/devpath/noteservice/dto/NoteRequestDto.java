package com.narcis.devpath.noteservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record NoteRequestDto(


    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @NotBlank
    String title,

    @NotBlank
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    String description,

    @NotNull
    Long moduleId

    )
{ }
