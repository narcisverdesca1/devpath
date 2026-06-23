package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.dto.CourseRequestDto;
import com.narcis.devpath.learningservice.dto.CourseResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.exception.ResourceNotFoundException;
import com.narcis.devpath.learningservice.mapper.CourseRequestMapper;
import com.narcis.devpath.learningservice.mapper.CourseResponseMapper;
import com.narcis.devpath.learningservice.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseResponseMapper courseResponseMapper;

    @Mock
    private CourseRequestMapper courseRequestMapper;

    @InjectMocks
    private CourseService courseService;


    @Test
    void createCourse_shouldSaveAndReturnCourseResponseDto() {
        CourseRequestDto requestDto = new CourseRequestDto("TestoCourse", "TestoDescrizione", "Beginner");
        Course course = new Course();
        course.setTitle("TestoCourse");
        course.setDescription("TestoDescrizione");
        course.setDifficulty("Beginner");
        CourseResponseDto responseDto = new CourseResponseDto(
                1L,
                "TestoCourse",
                "TestoDescrizione",
                "Beginner",
                null,
                null
        );

        when(courseRequestMapper.toEntity(requestDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseResponseMapper.toResponseDto(course)).thenReturn(responseDto);

        //ACT
        CourseResponseDto result = courseService.createCourse(requestDto);

        assertThat(result).isEqualTo(responseDto);


        verify(courseRequestMapper).toEntity(requestDto);
        verify(courseRepository).save(course);
        verify(courseResponseMapper).toResponseDto(course);
    }

    @Test
    void getCourseById_shouldReturnCourseResponseDto_whenCourseExists() {

        // Arrange - Cosa preparo
        Long id = 2L;
        Course course = new Course();
        course.setId(2L);
        course.setTitle("TestoCourse");
        course.setDescription("TestoDescrizione");
        course.setDifficulty("Beginner");

        CourseResponseDto responseDto = new CourseResponseDto(
                2L,
                "TestoCourse",
                "TestoDescrizione",
                "Beginner",
                null,
                null
        );

        // Mock - cosa fingo
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(courseResponseMapper.toResponseDto(course)).thenReturn(responseDto);

        // Act - cosa eseguo
        CourseResponseDto result = courseService.getCourseById(id);

        // Assert - cosa mi aspetto
        assertThat(result).isEqualTo(responseDto);

        // Verify - quali collaboratori devono essere stati chiamati
        verify(courseRepository).findById(id);
        verify(courseResponseMapper).toResponseDto(course);
    }

    @Test
    void getCourseById_shouldThrowResourceNotFoundException_whenCourseDoesNotExist() {
        // Arrange - Cosa preparo
        Long id = 3L;

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.getCourseById(id)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Course not found: " + id);

        verify(courseRepository).findById(id);

    }


    @Test
    void deleteCourse_shouldDeleteExistingCourse() {
        Long id = 2L;
        Course course = new Course();
        course.setId(2L);
        course.setTitle("TestoCourse");
        course.setDescription("TestoDescrizione");
        course.setDifficulty("Beginner");


        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        courseService.deleteCourse(id);

        verify(courseRepository).findById(id);
        verify(courseRepository).deleteById(id);
    }

    @Test
    void deleteCourse_shouldThrowResourceNotFoundException_whenCourseDoesNotExist(){
        Long id = 3L;
        when(courseRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> courseService.deleteCourse(id)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Course not found: " + id);
        verify(courseRepository).findById(id);
        verify(courseRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateCourse_shouldUpdateExistingCourse() {
        // Arrange
        Long id = 2L;

        CourseRequestDto requestDto = new CourseRequestDto(
                "TestoCourseUpdated",
                "TestoDescrizione",
                "Beginner"
        );

        Course existingCourse = new Course();
        existingCourse.setId(id);
        existingCourse.setTitle("TestoCourse");
        existingCourse.setDescription("TestoDescrizione");
        existingCourse.setDifficulty("Beginner");

        CourseResponseDto responseDto = new CourseResponseDto(
                id,
                "TestoCourseUpdated",
                "TestoDescrizione",
                "Beginner",
                null,
                null
        );

        // Mock
        when(courseRepository.findById(id)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);
        when(courseResponseMapper.toResponseDto(existingCourse)).thenReturn(responseDto);

        // Act
        CourseResponseDto result = courseService.updateCourse(id, requestDto);

        // Assert
        assertThat(result).isEqualTo(responseDto);

        // Verify
        verify(courseRepository).findById(id);
        verify(courseRequestMapper).updateEntityFromRequest(requestDto, existingCourse);
        verify(courseRepository).save(existingCourse);
        verify(courseResponseMapper).toResponseDto(existingCourse);
    }

}
