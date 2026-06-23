package com.narcis.devpath.authenticationservice.mapper;


import com.narcis.devpath.authenticationservice.dto.RegisterRequestDto;
import com.narcis.devpath.authenticationservice.dto.RegisterResponseDto;
import com.narcis.devpath.authenticationservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequestDto request);

    RegisterResponseDto toRegisterResponseDto(User user);
}
