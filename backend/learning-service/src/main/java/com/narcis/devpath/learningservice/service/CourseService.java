package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.dto.CourseRequestDto;
import com.narcis.devpath.learningservice.dto.CourseResponseDto;
import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.exception.ResourceNotFoundException;
import com.narcis.devpath.learningservice.mapper.CourseRequestMapper;
import com.narcis.devpath.learningservice.mapper.CourseResponseMapper;
import com.narcis.devpath.learningservice.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final CourseResponseMapper courseResponseMapper;
    private final CourseRequestMapper courseRequestMapper;

    public CourseService(CourseRepository courseRepository, CourseResponseMapper courseResponseMapper, CourseRequestMapper courseRequestMapper) {
        this.courseRepository = courseRepository;
        this.courseResponseMapper = courseResponseMapper;
        this.courseRequestMapper = courseRequestMapper;
    }

    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        Course course = courseRequestMapper.toEntity(courseRequestDto);
        Course savedCourse = courseRepository.save(course);

        return courseResponseMapper.toResponseDto(savedCourse);
    }

    public List<CourseResponseDto> getAllCourses(){
        List<Course> courses = courseRepository.findAll();

        return courseResponseMapper.toResponseDtoList(courses);
    }

    public CourseResponseDto getCourseById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course not found: " + id));

        return courseResponseMapper.toResponseDto(course);
    }

    public void deleteCourse(Long id){
        courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        courseRepository.deleteById(id);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto courseUpdatedRequestDto) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        courseRequestMapper.updateEntityFromRequest(courseUpdatedRequestDto, existingCourse);
        Course updatedCourse = courseRepository.save(existingCourse);

        return courseResponseMapper.toResponseDto(updatedCourse);
    }
}
