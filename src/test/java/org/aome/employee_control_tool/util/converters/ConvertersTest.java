package org.aome.employee_control_tool.util.converters;

import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConvertersTest {
    @InjectMocks
    private Converters converters;
    @Mock
    private static CEOSaver ceoSaver;

    private static AuthenticationDTO authenticationDTO;
    private static UserEntity userEntity;
    private static EmployeeDTO employeeDTO;
    private static EmployeeEntity employeeEntity;

    @BeforeAll
    static void init(){
        ceoSaver = new CEOSaver();
        authenticationDTO = AuthenticationDTO.builder()
                .username("admin")
                .password("admin")
                .build();
        userEntity = UserEntity.builder()
                .username("admin")
                .password("admin")
                .build();
        employeeDTO = EmployeeDTO.builder()
                .firstName("Admin")
                .lastName("Admin")
                .position("Administrator")
                .department("Administrators")
                .dateOfHire(LocalDate.now())
                .ceo(new byte[]{11})
                .build();
        employeeEntity = EmployeeEntity.builder()
                .firstName("Admin")
                .lastName("Admin")
                .position("Administrator")
                .department("Administrators")
                .dateOfHire(LocalDate.now())
                .ceoLink("SomeLink")
                .build();
    }
    @Test
    void authenticationDtoToEntity() {
        UserEntity testingUser = Converters.authenticationDtoToEntity(authenticationDTO);
        assertEquals(testingUser.getUsername(), userEntity.getUsername());
        assertEquals(testingUser.getPassword(), userEntity.getPassword());
    }

    @Test
    void employeeDtoToEntity() throws IOException {


    }

    @Test
    void employeeEntityToDto() {
    }
}