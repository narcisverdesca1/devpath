package com.narcis.devpath.learningservice.repository;

import com.narcis.devpath.learningservice.TestDataFactory;
import com.narcis.devpath.learningservice.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
public class CourseRepositoryIT {

    @Autowired
    private CourseRepository courseRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Test
    void saveCourse_shouldPersistCourse() {

        // Arrange
        Course course = new Course();
        course.setTitle("Course Title");
        course.setDescription("Course Description");
        course.setDifficulty("Difficulty");

        // Act
        Course savedCourse = courseRepository.save(course);
        Optional<Course> foundCourse = courseRepository.findById(savedCourse.getId());

        // Assert
        assertThat(savedCourse.getId()).isNotNull();
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getTitle()).isEqualTo("Course Title");
        assertThat(foundCourse.get().getDescription()).isEqualTo("Course Description");
        assertThat(foundCourse.get().getDifficulty()).isEqualTo("Difficulty");

    }

    @Test
    void findCourseById_shouldReturnCourse_whenCourseExists() {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Course Title");
        course.setDescription("Course Description");
        course.setDifficulty("Difficulty");

        Course savedCourse = courseRepository.save(course);
        Optional<Course> foundCourse = courseRepository.findById(savedCourse.getId());
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getTitle()).isEqualTo("Course Title");
        assertThat(foundCourse.get().getDescription()).isEqualTo("Course Description");
        assertThat(foundCourse.get().getDifficulty()).isEqualTo("Difficulty");
    }

    @Test
    void deleteCourse_shouldRemoveCourse_whenCourseExists() {
        // Arrange
        Course course = new Course();
        course.setTitle("Course Title");
        course.setDescription("Course Description");
        course.setDifficulty("Difficulty");

        Course savedCourse = courseRepository.save(course);

        // Act
        courseRepository.deleteById(savedCourse.getId());

        // Assert
        Optional<Course> foundCourse = courseRepository.findById(savedCourse.getId());

        assertThat(foundCourse).isNotPresent();
    }
}
