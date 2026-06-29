package com.narcis.devpath.noteservice.mapper;

import com.narcis.devpath.noteservice.dto.NoteRequestDto;
import com.narcis.devpath.noteservice.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteRequestMapper {
    Note toEntity(NoteRequestDto noteRequestDto);

    void updateEntityFromRequest(
            NoteRequestDto noteRequestDto,
            @MappingTarget Note note
    );
}
