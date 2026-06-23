package com.narcis.devpath.learningservice.repository;

import com.narcis.devpath.learningservice.entity.Course;
import com.narcis.devpath.learningservice.entity.Module;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@Testcontainers
public class ModuleRepositoryIT {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void saveModule_shouldPersistModuleWithCourse() {

        // Arrange
        Course course = new Course();
        course.setTitle("Java");
        course.setDescription("Java Course");
        course.setDifficulty("BEGINNER");

        Course savedCourse = courseRepository.save(course);

        Module module = new Module();
        module.setTitle("Variables");
        module.setDescription("Variables module");
        module.setPosition(1);
        module.setCourse(savedCourse);

        // Act
        Module savedModule = moduleRepository.save(module);

        Optional<Module> foundModule =
                moduleRepository.findById(savedModule.getId());

        // Assert
        assertThat(savedModule.getId()).isNotNull();
        assertThat(foundModule).isPresent();
        assertThat(foundModule.get().getTitle()).isEqualTo("Variables");
        assertThat(foundModule.get().getDescription()).isEqualTo("Variables module");
        assertThat(foundModule.get().getPosition()).isEqualTo(1);
        assertThat(foundModule.get().getCourse().getId()).isEqualTo(savedCourse.getId());
    }

    @Test
    void findByCourseId_shouldReturnModulesOfCourse() {

        // Arrange
        Course course = new Course();
        course.setTitle("Java");
        course.setDescription("Java Course");
        course.setDifficulty("BEGINNER");

        Course savedCourse = courseRepository.save(course);

        Module module1 = new Module();
        module1.setTitle("Variables1");
        module1.setDescription("Variables module1");
        module1.setPosition(1);
        module1.setCourse(savedCourse);

        Module module2 = new Module();
        module2.setTitle("Variables2");
        module2.setDescription("Variables module2");
        module2.setPosition(2);
        module2.setCourse(savedCourse);

        moduleRepository.save(module1);
        moduleRepository.save(module2);

        // Act
        List<Module> modulesFound =
                moduleRepository.findByCourseId(savedCourse.getId());

        // Assert
        assertThat(modulesFound).hasSize(2);

        assertThat(modulesFound)
                .extracting(Module::getTitle)
                .containsExactlyInAnyOrder("Variables1", "Variables2");

        assertThat(modulesFound)
                .extracting(module -> module.getCourse().getId())
                .containsOnly(savedCourse.getId());
    }

    @Test
    void deleteModule_shouldRemoveModule_whenModuleExists() {

        // Arrange
        Course course = new Course();
        course.setTitle("Java");
        course.setDescription("Java Course");
        course.setDifficulty("BEGINNER");

        Course savedCourse = courseRepository.save(course);

        Module module = new Module();
        module.setTitle("Variables");
        module.setDescription("Variables module");
        module.setPosition(1);
        module.setCourse(savedCourse);

        Module savedModule = moduleRepository.save(module);

        // Act
        moduleRepository.deleteById(savedModule.getId());

        // Assert
        Optional<Module> foundModule =
                moduleRepository.findById(savedModule.getId());

        assertThat(foundModule).isEmpty();
    }

    @Test
    void saveModule_shouldPersistCourseRelationship() {

        // Arrange
        Course course = new Course();
        course.setTitle("Java");
        course.setDescription("Java Course");
        course.setDifficulty("BEGINNER");

        Course savedCourse = courseRepository.save(course);

        Module module = new Module();
        module.setTitle("OOP");
        module.setDescription("Object oriented programming");
        module.setPosition(2);
        module.setCourse(savedCourse);

        // Act
        Module savedModule = moduleRepository.save(module);

        Optional<Module> foundModule =
                moduleRepository.findById(savedModule.getId());

        // Assert
        assertThat(foundModule).isPresent();
        assertThat(foundModule.get().getCourse()).isNotNull();
        assertThat(foundModule.get().getCourse().getId()).isEqualTo(savedCourse.getId());
    }
}
