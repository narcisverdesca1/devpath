package com.narcis.devpath.learningservice.service;

import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.exception.ResourceNotFoundException;
import com.narcis.devpath.learningservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id){
        return Optional.of(courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id)));
    }

    public void deleteCourse(Long id){
        courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        courseRepository.deleteById(id);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDifficulty(updatedCourse.getDifficulty());

        return courseRepository.save(existingCourse);
    }
}
