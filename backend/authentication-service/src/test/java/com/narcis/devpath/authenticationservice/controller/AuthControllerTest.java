package com.narcis.devpath.authenticationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.narcis.devpath.authenticationservice.config.JwtAuthenticationFilter;
import com.narcis.devpath.authenticationservice.dto.RegisterRequestDto;
import com.narcis.devpath.authenticationservice.dto.RegisterResponseDto;
import com.narcis.devpath.authenticationservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void register_shouldReturnCreated_whenRequestIsValid() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto(
                "mario.rossi@test.com",
                "password123",
                "Mario",
                "Rossi"
        );

        RegisterResponseDto response = RegisterResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@test.com")
                .build();

        when(authService.register(any(RegisterRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Mario"))
                .andExpect(jsonPath("$.lastName").value("Rossi"))
                .andExpect(jsonPath("$.email").value("mario.rossi@test.com"));
    }
}
