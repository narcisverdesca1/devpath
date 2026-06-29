package com.narcis.devpath.noteservice.controller;

import com.narcis.devpath.noteservice.dto.NoteRequestDto;
import com.narcis.devpath.noteservice.dto.NoteResponseDto;
import com.narcis.devpath.noteservice.entity.Note;
import com.narcis.devpath.noteservice.exception.ApiError;
import com.narcis.devpath.noteservice.repository.NoteRepository;
import com.narcis.devpath.noteservice.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Notes management APIs")
public class NoteController {

    private final NoteService noteService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public String testNoteJWTApiGateway(){
        return "Admin access";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Note")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Note created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })

    public NoteResponseDto createNote(@Valid @RequestBody NoteRequestDto noteRequestDto){
        return noteService.createNote(noteRequestDto);
    }

}
