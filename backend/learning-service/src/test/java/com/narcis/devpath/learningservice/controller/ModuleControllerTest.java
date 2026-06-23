package com.narcis.devpath.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.narcis.devpath.learningservice.TestDataFactory;
import com.narcis.devpath.learningservice.dto.ModuleRequestDto;
import com.narcis.devpath.learningservice.dto.ModuleResponseDto;
import com.narcis.devpath.learningservice.service.ModuleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModuleController.class)
class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModuleService moduleService;

    @Test
    void createModule_shouldReturnCreatedModule() throws Exception {
        Long courseId = 1L;
        ModuleRequestDto requestDto = TestDataFactory.moduleRequestDto();
        ModuleResponseDto responseDto = TestDataFactory.moduleResponseDto(1L);

        when(moduleService.createModule(courseId, requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/courses/{courseId}/modules", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.title").value(responseDto.title()))
                .andExpect(jsonPath("$.description").value(responseDto.description()))
                .andExpect(jsonPath("$.position").value(responseDto.position()));

        verify(moduleService).createModule(courseId, requestDto);
    }

    @Test
    void findModulesByCourseId_shouldReturnModuleList() throws Exception {
        Long courseId = 1L;
        List<ModuleResponseDto> responseDtoList = List.of(
                TestDataFactory.moduleResponseDto(1L),
                TestDataFactory.moduleResponseDto(2L)
        );

        when(moduleService.findModulesByCourseId(courseId)).thenReturn(responseDtoList);

        mockMvc.perform(get("/courses/{courseId}/modules", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(moduleService).findModulesByCourseId(courseId);
    }

    @Test
    void findModuleById_shouldReturnModule() throws Exception {
        Long moduleId = 1L;
        ModuleResponseDto responseDto = TestDataFactory.moduleResponseDto(moduleId);

        when(moduleService.findModuleById(moduleId)).thenReturn(responseDto);

        mockMvc.perform(get("/modules/{id}", moduleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.title").value(responseDto.title()))
                .andExpect(jsonPath("$.description").value(responseDto.description()))
                .andExpect(jsonPath("$.position").value(responseDto.position()));

        verify(moduleService).findModuleById(moduleId);
    }

    @Test
    void updateModuleById_shouldReturnUpdatedModule() throws Exception {
        Long moduleId = 1L;
        ModuleRequestDto requestDto = ModuleRequestDto.builder()
                .title("Updated Module")
                .description("Updated Module Description")
                .position(2)
                .build();
        ModuleResponseDto responseDto = ModuleResponseDto.builder()
                .id(moduleId)
                .title("Updated Module")
                .description("Updated Module Description")
                .position(2)
                .build();

        when(moduleService.updateModuleById(moduleId, requestDto)).thenReturn(responseDto);

        mockMvc.perform(put("/modules/{id}", moduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.title").value(responseDto.title()))
                .andExpect(jsonPath("$.description").value(responseDto.description()))
                .andExpect(jsonPath("$.position").value(responseDto.position()));

        verify(moduleService).updateModuleById(moduleId, requestDto);
    }

    @Test
    void deleteModuleById_shouldReturnOk() throws Exception {
        Long moduleId = 1L;

        mockMvc.perform(delete("/modules/{id}", moduleId))
                .andExpect(status().isOk());

        verify(moduleService).deleteModuleById(moduleId);
    }

    @Test
    void createModule_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        Long courseId = 1L;
        ModuleRequestDto requestDto = new ModuleRequestDto("", "Invalid Module", null);

        mockMvc.perform(post("/courses/{courseId}/modules", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
