package com.narcis.devpath.learningservice.mapper;

import com.narcis.devpath.learningservice.dto.CourseRequestDto;
import com.narcis.devpath.learningservice.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseRequestMapper {
    Course toEntity(CourseRequestDto request);

    void updateEntityFromRequest(
            CourseRequestDto request,
            @MappingTarget Course course
    );
}
