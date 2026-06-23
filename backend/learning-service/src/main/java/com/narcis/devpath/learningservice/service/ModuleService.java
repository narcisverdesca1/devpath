package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.exception.ResourceNotFoundException;
import com.narcis.devpath.learningservice.mapper.ModuleRequestMapper;
import com.narcis.devpath.learningservice.mapper.ModuleResponseMapper;
import com.narcis.devpath.learningservice.repository.CourseRepository;
import com.narcis.devpath.learningservice.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.narcis.devpath.learningservice.entity.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModuleRequestMapper moduleRequestMapper;
    private final ModuleResponseMapper moduleResponseMapper;


    public ModuleResponseDto createModule(Long courseId, ModuleRequestDto moduleRequestDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Module module = moduleRequestMapper.toEntity(moduleRequestDto);

        module.setCourse(course);

        Module moduleSaved = moduleRepository.save(module);

        return moduleResponseMapper.toResponseDto(moduleSaved);
    }

    public List<ModuleResponseDto> findModulesByCourseId(Long courseId){
        List<Module> modules =  moduleRepository.findByCourseId(courseId);

        return moduleResponseMapper.toResponseDtoList(modules);
    }

    public ModuleResponseDto findModuleById(Long id){
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        return moduleResponseMapper.toResponseDto(module);
    }

    public ModuleResponseDto updateModuleById(Long id, ModuleRequestDto moduleRequestDto) {

        Module existingModule = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        moduleRequestMapper.updateEntityFromRequest(moduleRequestDto, existingModule);

        Module updatedModule = moduleRepository.save(existingModule);

        return moduleResponseMapper.toResponseDto(updatedModule);

    }

    public void deleteModuleById(Long id){
        moduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));
        moduleRepository.deleteById(id);
    }
}
