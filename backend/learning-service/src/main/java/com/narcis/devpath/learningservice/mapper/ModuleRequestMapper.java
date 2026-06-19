package com.narcis.devpath.learningservice.mapper;

import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModuleRequestMapper {
    Module toEntity(ModuleRequestDto request);

    void updateEntityFromRequest(
            ModuleRequestDto request,
            @MappingTarget Module module
    );
}
