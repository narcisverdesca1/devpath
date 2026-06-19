package com.narcis.devpath.learningservice.controller;

import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/courses/{courseId}/modules")
    public ModuleResponseDto createModule(@PathVariable Long courseId,
                                          @Valid @RequestBody ModuleRequestDto moduleRequestDto) {
        return moduleService.createModule(courseId, moduleRequestDto);
    }

    @GetMapping("/courses/{courseId}/modules")
    public List<ModuleResponseDto> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.findModulesByCourseId(courseId);
    }

    @GetMapping("/modules/{id}")
    public ModuleResponseDto getModuleById(@PathVariable Long id) {
        return moduleService.findModuleById(id);
    }

    @PutMapping("/modules/{id}")
    public ModuleResponseDto updateModule(@PathVariable Long id,
                               @Valid @RequestBody ModuleRequestDto moduleRequestDto) {
        return moduleService.updateModuleById(id, moduleRequestDto);
    }

    @DeleteMapping("/modules/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModuleById(id);
    }
}
