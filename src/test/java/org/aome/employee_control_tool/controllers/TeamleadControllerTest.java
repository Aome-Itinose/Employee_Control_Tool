package org.aome.employee_control_tool.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aome.employee_control_tool.dtos.TimeSheetDTO;
import org.aome.employee_control_tool.dtos.VacationDTO;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.security.UserSecurityDetailsService;
import org.aome.employee_control_tool.services.EmployeeService;
import org.aome.employee_control_tool.services.TimeSheetService;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.services.VacationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TeamleadController.class)
@AutoConfigureMockMvc(addFilters = false)
class TeamleadControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimeSheetService timeSheetService;
    @Mock
    private BindingResult bindingResult;
    @MockBean
    private VacationService vacationService;

    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private UserService userService;
    @MockBean
    private UserSecurityDetailsService userSecurityDetailsService;
    @MockBean
    private EmployeeService employeeService;

    @Test
    void addTimeSheet_Ok() throws Exception{
        TimeSheetDTO timeSheetDTO = TimeSheetDTO.builder()
                .employeeId(UUID.fromString("8a3e79ab-7f3c-4f4f-ada4-2a2191594b0f"))
                .date(LocalDate.now())
                .hoursWorked(12)
                .efficiency(2.0)
                .build();

        given(employeeService.existsById(any())).willReturn(true);

        ResultActions result = mockMvc.perform(post("/teamlead/timesheet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(timeSheetDTO))
        );
        result.andExpect(status().isOk());
        verify(timeSheetService, times(1)).saveAndConnectWithEmployee(timeSheetDTO);
    }
    @Test
    void addTimeSheet_BadRequest_TimeSheetNotCreated() throws Exception {
        TimeSheetDTO timeSheetDTO = TimeSheetDTO.builder()
                .employeeId(UUID.fromString("8a3e79ab-7f3c-4f4f-ada4-2a2191594b0f"))
                .date(LocalDate.now())
                .hoursWorked(12)
                .efficiency(2.0)
                .build();

        given(employeeService.existsById(any())).willReturn(false);

        ResultActions result = mockMvc.perform(post("/teamlead/timesheet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(timeSheetDTO))
        );
        result.andExpect(status().isBadRequest());

    }

    @Test
    void addVacation_Ok() throws Exception {
        VacationDTO vacationDTO = VacationDTO.builder()
                .employeeId(UUID.fromString("8a3e79ab-7f3c-4f4f-ada4-2a2191594b0f"))
                .startDate(LocalDate.of(2023, Month.JANUARY, 21))
                .endDate(LocalDate.of(2023, Month.JANUARY, 22))
                .build();

        given(employeeService.existsById(any())).willReturn(true);

        ResultActions result = mockMvc.perform(post("/teamlead/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationDTO))
        );
        result.andExpect(status().isOk());
        verify(vacationService, times(1)).saveAndConnectWithEmployee(vacationDTO);
    }
    @Test
    void addVacation_BadRequest_VacationNotCreated() throws Exception {
        VacationDTO vacationDTO = VacationDTO.builder()
                .employeeId(UUID.fromString("8a3e79ab-7f3c-4f4f-ada4-2a2191594b0f"))
                .startDate(LocalDate.of(2023, Month.JANUARY, 21))
                .endDate(LocalDate.of(2023, Month.JANUARY, 22))
                .build();

        given(employeeService.existsById(any())).willReturn(false);

        ResultActions result = mockMvc.perform(post("/teamlead/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationDTO))
        );
        result.andExpect(status().isBadRequest());
    }
}