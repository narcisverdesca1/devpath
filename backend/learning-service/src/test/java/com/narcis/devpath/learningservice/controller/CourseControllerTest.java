package com.narcis.devpath.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.narcis.devpath.learningservice.TestDataFactory;
import com.narcis.devpath.learningservice.dto.CourseRequestDto;
import com.narcis.devpath.learningservice.dto.CourseResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CourseService courseService;

    @Test
    void createCourse_shouldReturnCreatedCourse() throws Exception {
        // Arrange
        CourseRequestDto requestDto = TestDataFactory.courseRequestDto();
        CourseResponseDto responseDto = TestDataFactory.courseResponseDto(1L);

        when(courseService.createCourse(any(CourseRequestDto.class)))
                .thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(responseDto.title()))
                .andExpect(jsonPath("$.description").value(responseDto.description()))
                .andExpect(jsonPath("$.difficulty").value(responseDto.difficulty()));

        verify(courseService).createCourse(any(CourseRequestDto.class));
    }

    @Test
    void getCourseById_shouldReturnCourse() throws Exception {

        Long courseId = 1L;
        Course course = TestDataFactory.course(courseId);
        course.setTitle("test");
        course.setDescription("test");
        course.setDifficulty("test");

        CourseResponseDto responseDto = CourseResponseDto.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .difficulty(course.getDifficulty())
                .build();

        when(courseService.getCourseById(courseId)).thenReturn(responseDto);

        mockMvc.perform(
                get("/courses/{id}",courseId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(course.getTitle()))
                .andExpect(jsonPath("$.description").value(course.getDescription()));
        verify(courseService).getCourseById(courseId);
    }

    @Test
    void getAllCourses_shouldReturnCourseList() throws Exception {
        // Arrange
        List<CourseResponseDto> responseDtoList = List.of(
                TestDataFactory.courseResponseDto(1L),
                TestDataFactory.courseResponseDto(2L),
                TestDataFactory.courseResponseDto(3L)
        );

        when(courseService.getAllCourses()).thenReturn(responseDtoList);

        // Act & Assert
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));

        verify(courseService).getAllCourses();
    }

    @Test
    void createCourse_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        // Arrange
        CourseRequestDto requestDto = new CourseRequestDto("","","");

        // Act & Assert
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void updateCourse_shouldReturnUpdatedCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("title");
        course.setDescription("description");
        course.setDifficulty("difficulty");

        CourseRequestDto requestDto = CourseRequestDto.builder()
                .title("test")
                .description("test")
                .difficulty("test")
                .build();

        CourseResponseDto responseDto = CourseResponseDto.builder()
                .title("test")
                .description("test")
                .difficulty("test")
                .build();

        when(courseService.updateCourse(course.getId(),requestDto)).thenReturn(responseDto);

        mockMvc.perform(
                put("/courses/{id}",course.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk()).andExpect(jsonPath("$.title").value(responseDto.title())).andExpect(jsonPath("$.description").value(responseDto.description()));
    }

    @Test
    void deleteCourse_shouldReturnNoContent() throws Exception {
        Course course = new Course();
        course.setId(1L);

        mockMvc.perform(delete("/courses/{id}",course.getId())).andExpect(status().isOk());

    }

}