package com.narcis.devpath.noteservice.service;

import com.narcis.devpath.noteservice.dto.ModuleSummaryDto;
import com.narcis.devpath.noteservice.dto.NoteRequestDto;
import com.narcis.devpath.noteservice.dto.NoteResponseDto;
import com.narcis.devpath.noteservice.entity.Note;
import com.narcis.devpath.noteservice.mapper.NoteRequestMapper;
import com.narcis.devpath.noteservice.mapper.NoteResponseMapper;
import com.narcis.devpath.noteservice.client.ModuleClient;
import com.narcis.devpath.noteservice.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRequestMapper requestMapper;
    private final NoteResponseMapper responseMapper;
    private final NoteRepository noteRepository;
    private final ModuleClient moduleClient;


    public NoteResponseDto createNote(NoteRequestDto noteRequestDto) {
        Note noteToSave = requestMapper.toEntity(noteRequestDto);

        noteToSave.setOwnerId(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());

        ModuleSummaryDto moduleSummaryDto = moduleClient.getModuleSummary(noteRequestDto.moduleId());
        noteToSave.setModuleTitleSnapshot(moduleSummaryDto.title());
        Note noteSaved = noteRepository.save(noteToSave);

        return responseMapper.toNoteResponseDto(noteSaved);
    }
}
