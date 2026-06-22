package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.TestDataFactory;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.entity.Module;
import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.exception.ResourceNotFoundException;
import com.narcis.devpath.learningservice.mapper.ModuleRequestMapper;
import com.narcis.devpath.learningservice.mapper.ModuleResponseMapper;
import com.narcis.devpath.learningservice.repository.CourseRepository;
import com.narcis.devpath.learningservice.repository.ModuleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ModuleServiceTest {

    @Mock
    private  ModuleRepository moduleRepository;
    @Mock
    private  CourseRepository courseRepository;
    @Mock
    private  ModuleRequestMapper moduleRequestMapper;
    @Mock
    private  ModuleResponseMapper moduleResponseMapper;
    @InjectMocks
    private ModuleService moduleService;


    @Test
    void createModule_shouldSaveAndReturnModuleResponseDto_whenCourseExists() {
        Long courseId = 1L;

        Course course = new Course();
        course.setId(courseId);
        ModuleRequestDto moduleRequestDto = new ModuleRequestDto(
          "Title",
                "description",
                1
        );

        Module moduleToSave = new Module();
        moduleToSave.setTitle("Title");
        moduleToSave.setDescription("Description");
        moduleToSave.setPosition(1);
        moduleToSave.setCourse(course);

        ModuleResponseDto moduleResponseDto = ModuleResponseDto.builder()
                .id(1L)
                .title("Title")
                .description("Description")
                .position(1)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(moduleRequestMapper.toEntity(moduleRequestDto)).thenReturn(moduleToSave);
        when(moduleRepository.save(moduleToSave)).thenReturn(moduleToSave);
        when(moduleResponseMapper.toResponseDto(moduleToSave)).thenReturn(moduleResponseDto);

        ModuleResponseDto result = moduleService.createModule(courseId, moduleRequestDto);

        assertThat(result).isEqualTo(moduleResponseDto);

        verify(courseRepository).findById(courseId);
        verify(moduleRequestMapper).toEntity(moduleRequestDto);
        verify(moduleRepository).save(moduleToSave);
        verify(moduleResponseMapper).toResponseDto(moduleToSave);
    }

    @Test
    void createModule_shouldThrowResourceNotFoundException_whenCourseDoesNotExist() {
        Long courseId = 1L;

        ModuleRequestDto moduleRequestDto = new ModuleRequestDto(
                "Title",
                "description",
                1
        );

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> moduleService.createModule(courseId, moduleRequestDto)).isInstanceOf(ResourceNotFoundException.class);

        verify(courseRepository).findById(courseId);

    }

    @Test
    void findModulesByCourseId_shouldReturnModuleResponseDtoList() {

        Long courseId = 1L;

        List<Module> moduleList = new ArrayList<>();
        moduleList.add(TestDataFactory.module(1L,TestDataFactory.course(courseId)));
        moduleList.add(TestDataFactory.module(2L,TestDataFactory.course(courseId)));
        moduleList.add(TestDataFactory.module(3L,TestDataFactory.course(courseId)));

        List<ModuleResponseDto> moduleResponseDtoList = new ArrayList<>();
        moduleResponseDtoList.add(TestDataFactory.moduleResponseDto(1L));
        moduleResponseDtoList.add(TestDataFactory.moduleResponseDto(2L));
        moduleResponseDtoList.add(TestDataFactory.moduleResponseDto(3L));


        when(moduleRepository.findByCourseId(courseId)).thenReturn(moduleList);
        when(moduleResponseMapper.toResponseDtoList(moduleList)).thenReturn(moduleResponseDtoList);

        List<ModuleResponseDto> moduleResponseDtoListResult = moduleService.findModulesByCourseId(courseId);

        assertThat(moduleResponseDtoListResult).isEqualTo(moduleResponseDtoList);

        verify(moduleRepository).findByCourseId(courseId);
        verify(moduleResponseMapper).toResponseDtoList(moduleList);
    }

    @Test
    void findModuleById_shouldReturnModuleResponseDto_whenModuleExists() {
        Long moduleId = 1L;

        Module module = TestDataFactory.module(1L,TestDataFactory.course(1L));

        ModuleResponseDto moduleResponseDto = TestDataFactory.moduleResponseDto(1L);


        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(module));
        when(moduleResponseMapper.toResponseDto(module)).thenReturn(moduleResponseDto);

        ModuleResponseDto result = moduleService.findModuleById(moduleId);

        assertThat(result).isEqualTo(moduleResponseDto);

        verify(moduleRepository).findById(moduleId);
        verify(moduleResponseMapper).toResponseDto(module);

    }

    @Test
    void findModuleById_shouldThrowResourceNotFoundException_whenModuleDoesNotExist() {
        Long moduleId = 1L;
        when(moduleRepository.findById(moduleId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> moduleService.findModuleById(moduleId))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(moduleRepository).findById(moduleId);
    }

    @Test
    void updateModuleById_shouldUpdateExistingModule() {
        Long moduleId = 1L;
        ModuleRequestDto moduleRequestDto = TestDataFactory.moduleRequestDto();
        Module existingModule = TestDataFactory.module(1L,TestDataFactory.course(1L));
        ModuleResponseDto moduleResponseDto = TestDataFactory.moduleResponseDto(1L);

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(existingModule));
        when(moduleRepository.save(existingModule)).thenReturn(existingModule);
        when(moduleResponseMapper.toResponseDto(existingModule)).thenReturn(moduleResponseDto);

        ModuleResponseDto result = moduleService.updateModuleById(moduleId, moduleRequestDto);
        assertThat(result).isEqualTo(moduleResponseDto);
        verify(moduleRepository).findById(moduleId);
        verify(moduleResponseMapper).toResponseDto(existingModule);
    }

    @Test
    void updateModuleById_shouldThrowResourceNotFoundException_whenModuleDoesNotExist() {
        Long moduleId = 1L;
        when(moduleRepository.findById(moduleId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> moduleService.findModuleById(moduleId))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(moduleRepository).findById(moduleId);
    }

    @Test
    void deleteModuleById_shouldDeleteExistingModule() {
        // Arrange
        Long id = 2L;

        Course course = TestDataFactory.course(1L);
        Module module = TestDataFactory.module(id, course);

        // Mock
        when(moduleRepository.findById(id)).thenReturn(Optional.of(module));

        // Act
        moduleService.deleteModuleById(id);

        // Verify
        verify(moduleRepository).findById(id);
        verify(moduleRepository).deleteById(id);
    }

    @Test
    void deleteModuleById_shouldThrowResourceNotFoundException_whenModuleDoesNotExist() {
        Long id = 3L;
        when(moduleRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> moduleService
                .deleteModuleById(id)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Module not found with id: " + id);
        verify(moduleRepository).findById(id);
        verify(moduleRepository, never()).deleteById(anyLong());
    }
}
