package org.aome.employee_control_tool.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.dtos.VacationEmployeeDTO;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.security.UserSecurityDetailsService;
import org.aome.employee_control_tool.services.EmployeeService;
import org.aome.employee_control_tool.services.VacationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private VacationService vacationService;
    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private UserSecurityDetailsService userSecurityDetailsService;


    @Test
    void getEmployees_Ok() {
        EmployeeDTO employeeDTO1 = EmployeeDTO.builder()
                .firstName("Alex")
                .lastName("Goose")
                .build();
        EmployeeDTO employeeDTO2 = EmployeeDTO.builder()
                .firstName("John")
                .lastName("Stinger")
                .build();
        List<EmployeeDTO> employees = List.of(employeeDTO1, employeeDTO2);
        given(employeeService.findEmployeeDTOs(any(), anyInt(),anyInt())) //0, 10 - default values
                .willReturn(employees);
        try{
            ResultActions result = mockMvc.perform(get("/employee"));
            result
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(employees)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getEmployeeByVacation_Ok() {
        VacationEmployeeDTO vacationEmployeeDTO1 = VacationEmployeeDTO.builder()
                .startDate(LocalDate.of(2024, Month.JANUARY, 12))
                .endDate(LocalDate.of(2024, Month.FEBRUARY, 12))
                .build();
        VacationEmployeeDTO vacationEmployeeDTO2 = VacationEmployeeDTO.builder()
                .startDate(LocalDate.of(2023, Month.JANUARY, 12))
                .endDate(LocalDate.of(2023, Month.FEBRUARY, 12))
                .build();
        List<VacationEmployeeDTO> vacations = List.of(vacationEmployeeDTO1, vacationEmployeeDTO2);
        given(vacationService.findVacationDTOs(any(), anyInt(), anyInt())).willReturn(vacations);

        try{
            ResultActions result = mockMvc.perform(get("/employee/by-vacation"));
            result.andExpect(status().isOk()).andExpect(content().string(objectMapper.writeValueAsString(vacations)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}