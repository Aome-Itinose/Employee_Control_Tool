package org.aome.employee_control_tool.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.dtos.IdDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.exceptions.UserNotFoundException;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.security.UserSecurityDetailsService;
import org.aome.employee_control_tool.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DirectorController.class)
@AutoConfigureMockMvc(addFilters = false)
class DirectorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private UserSecurityDetailsService userSecurityDetailsService;
    @MockBean
    private EmployeeService employeeService;

    private IdDTO idDTO;
    @BeforeEach
    void setUp() {
        idDTO = IdDTO.builder().id(UUID.fromString("b79cb3ba-745e-5d9a-8903-4a02327a7e09")).build();
    }

    @Test
    void confirmEmployee_Ok() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id(idDTO.getId())
                .firstName("John")
                .lastName("Doe")
                .build();
        given(employeeService.saveEmployee(idDTO.getId())).willReturn(employeeDTO);
        try{
            ResultActions result = mockMvc.perform(post("/director/confirm-employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(idDTO))
            );
            result.andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void confirmEmployee_NotFound_UserNotFound() {
        given(employeeService.saveEmployee(idDTO.getId())).willThrow(UserNotFoundException.class);
        try{
            ResultActions result = mockMvc.perform(post("/director/confirm-employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(idDTO))
            );
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void confirmEmployee_NotFound_EmployeeNotFound() {
        given(employeeService.saveEmployee(idDTO.getId())).willThrow(EmployeeNotFoundException.class);
        try{
            ResultActions result = mockMvc.perform(post("/director/confirm-employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(idDTO))
            );
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}