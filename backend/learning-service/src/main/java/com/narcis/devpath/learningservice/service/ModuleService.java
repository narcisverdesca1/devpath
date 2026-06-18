package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.repository.CourseRepository;
import com.narcis.devpath.learningservice.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.narcis.devpath.learningservice.entity.Module;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    public Module createModule(Long courseId, Module module) {
        module.setCourse(courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId)));

        return moduleRepository.save(module);
    }

    public List<Module> findModulesByCourseId(Long courseId){
        return moduleRepository.findByCourseId(courseId);
    }

    public Optional<Module> findModuleById(Long id){
        return moduleRepository.findById(id);
    }

    public Module updateModuleById(Long id, Module updatedModule){
        Module moduleToUpdate = moduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Module not found with id: " + id)));
        moduleToUpdate.setTitle(updatedModule.getTitle());
        moduleToUpdate.setDescription(updatedModule.getDescription());
        moduleToUpdate.setPosition(updatedModule.getPosition());
        return moduleRepository.save(moduleToUpdate);
    }

    public void deleteModuleById(Long id){
        moduleRepository.deleteById(id);
    }
}
