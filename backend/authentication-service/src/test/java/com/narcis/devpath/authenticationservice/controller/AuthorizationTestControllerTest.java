package com.narcis.devpath.authenticationservice.controller;

import com.narcis.devpath.authenticationservice.config.SecurityConfig;
import com.narcis.devpath.authenticationservice.repository.UserRepository;
import com.narcis.devpath.authenticationservice.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestAuthorizationController.class)
@Import(SecurityConfig.class)
class TestAuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "USER")
    void userEndpoint_shouldReturnOk_whenUserRole() throws Exception {

        mockMvc.perform(get("/auth/test/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminEndpoint_shouldReturnForbidden_whenUserRole() throws Exception {

        mockMvc.perform(get("/auth/test/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminEndpoint_shouldReturnOk_whenAdminRole() throws Exception {

        mockMvc.perform(get("/auth/test/admin"))
                .andExpect(status().isOk());
    }
}
