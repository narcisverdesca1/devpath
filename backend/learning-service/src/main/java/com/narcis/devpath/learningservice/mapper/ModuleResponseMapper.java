package com.narcis.devpath.learningservice.mapper;

import com.narcis.devpath.learningservice.dto.CourseResponseDto;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.entity.Module;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModuleResponseMapper {
    ModuleResponseDto toResponseDto(Module module);

    List<ModuleResponseDto> toResponseDtoList(List<Module> modules);
}
