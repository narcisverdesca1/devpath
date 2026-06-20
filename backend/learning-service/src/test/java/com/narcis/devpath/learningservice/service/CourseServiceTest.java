package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.dto.CourseRequestDto;
import com.narcis.devpath.learningservice.dto.CourseResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.mapper.CourseRequestMapper;
import com.narcis.devpath.learningservice.mapper.CourseResponseMapper;
import com.narcis.devpath.learningservice.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}
