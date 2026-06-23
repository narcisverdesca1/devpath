package com.narcis.devpath.learningservice;

import com.narcis.devpath.learningservice.dto.CourseRequestDto;
import com.narcis.devpath.learningservice.dto.CourseResponseDto;
import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.entity.Module;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static Course course(Long id) {
        Course course = new Course();
        course.setId(id);
        course.setTitle("Test Course");
        course.setDescription("Test Description");
        course.setDifficulty("BEGINNER");
        return course;
    }

    public static CourseRequestDto courseRequestDto() {
        return new CourseRequestDto(
                "Test Course",
                "Test Description",
                "BEGINNER"
        );
    }

    public static CourseResponseDto courseResponseDto(Long id) {
        return new CourseResponseDto(
                id,
                "Test Course",
                "Test Description",
                "BEGINNER",
                null,
                null
        );
    }

    public static Module module(Long id, Course course) {
        Module module = new Module();
        module.setId(id);
        module.setTitle("Test Module");
        module.setDescription("Test Module Description");
        module.setPosition(1);
        module.setCourse(course);
        return module;
    }

    public static ModuleRequestDto moduleRequestDto() {
        return new ModuleRequestDto(
                "Test Module",
                "Test Module Description",
                1
        );
    }

    public static ModuleResponseDto moduleResponseDto(Long id) {
        return ModuleResponseDto.builder()
                .id(id)
                .title("Test Module")
                .description("Test Module Description")
                .position(1)
                .build();
    }
}
