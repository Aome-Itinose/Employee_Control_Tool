package org.aome.employee_control_tool.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.security.UserSecurityDetailsService;
import org.aome.employee_control_tool.services.AuthenticationService;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.util.validation.user.UsernameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.naming.AuthenticationException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private UserSecurityDetailsService userSecurityDetailsService;
    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private UsernameValidator usernameValidator;
    @MockBean
    private UserService userService;

    AuthenticationDTO authenticationDTO;

    @BeforeEach
    void setUp() {
        authenticationDTO = AuthenticationDTO.builder()
                .username("test")
                .password("test")
                .build();
    }

    @Test
    void login_Ok() throws AuthenticationException {
        given(authenticationService.authenticateAndReturnToken(authenticationDTO)).willReturn("someToken");
        try {
            ResultActions result = mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationDTO)));

            result
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value("someToken"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void login_BadGateway() throws AuthenticationException {
        given(authenticationService.authenticateAndReturnToken(authenticationDTO)).willThrow(AuthenticationException.class);
        try {
            ResultActions result = mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationDTO)));
            result.andExpect(status().isBadGateway());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void registration_Created() throws AuthenticationException {
        given(authenticationService.saveUserAndReturnToken(authenticationDTO)).willReturn("someToken");
        try{
            ResultActions result = mockMvc.perform(post("/auth/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationDTO))
            );
            result.andExpect(status().isCreated()).andExpect(jsonPath("$.token").value("someToken"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}