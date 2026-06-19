package com.narcis.devpath.learningservice.controller;

import com.narcis.devpath.learningservice.entity.Module;
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
    public Module createModule(@PathVariable Long courseId,
                               @Valid @RequestBody Module module) {
        return moduleService.createModule(courseId, module);
    }

    @GetMapping("/courses/{courseId}/modules")
    public List<Module> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.findModulesByCourseId(courseId);
    }

    @GetMapping("/modules/{id}")
    public Module getModuleById(@PathVariable Long id) {
        return moduleService.findModuleById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));
    }

    @PutMapping("/modules/{id}")
    public Module updateModule(@PathVariable Long id,
                               @Valid @RequestBody Module module) {
        return moduleService.updateModuleById(id, module);
    }

    @DeleteMapping("/modules/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModuleById(id);
    }
}
