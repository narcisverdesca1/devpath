package com.narcis.devpath.noteservice.mapper;

import com.narcis.devpath.noteservice.dto.NoteResponseDto;
import com.narcis.devpath.noteservice.entity.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteResponseMapper {
    NoteResponseDto toNoteResponseDto(Note note);

    List<NoteResponseDto> toNoteResponseDtoList(List<Note> notes);
}
