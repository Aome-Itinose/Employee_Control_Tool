package org.aome.employee_control_tool.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.security.UserDetailsHolder;
import org.aome.employee_control_tool.security.UserSecurityDetailsService;
import org.aome.employee_control_tool.services.EmployeeService;
import org.aome.employee_control_tool.services.TestEmployeeService;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.util.validation.employee.BecomeEmployeeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestEmployeeService testEmployeeService;
    @MockBean
    private BecomeEmployeeValidator becomeEmployeeValidator;

    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private UserService userService;
    @MockBean
    private UserSecurityDetailsService userSecurityDetailsService;
    @MockBean
    private UserDetailsHolder userDetailsHolder;

    EmployeeDTO employeeDTO;
    @BeforeEach
    void setUp() {
        UUID uuid = UUID.fromString("8a3e79ab-7f3c-4f4f-ada4-2a2191594b0f");
        employeeDTO = EmployeeDTO.builder()
                .id(uuid)
                .firstName("Test")
                .lastName("Test")
                .position("Test")
                .department("Test")
                .dateOfHire(LocalDate.now())
                .ceo(new byte[]{1, 2, 3})
                .build();
    }

    @Test
    void becomeEmployee_Ok() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity user = UserEntity.builder().id(userId).build();
        given(userDetailsHolder.getUserFromSecurityContext()).willReturn(user);
        ResultActions resultActions = mockMvc.perform(post("/user/become-employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO))
        );
        resultActions.andExpect(status().isCreated());
        verify(becomeEmployeeValidator,times(1)).validate(any(), any());
        verify(testEmployeeService, times(1)).saveAndConnectWithUser(employeeDTO, user.getId());
    }
}