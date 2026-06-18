package com.narcis.devpath.learningservice.repository;

import com.narcis.devpath.learningservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
