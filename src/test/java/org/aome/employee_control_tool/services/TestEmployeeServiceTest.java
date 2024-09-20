package org.aome.employee_control_tool.services;

import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.TestEmployeeRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TestEmployeeServiceTest {
    @InjectMocks
    private TestEmployeeService testEmployeeService;

    @Mock
    private TestEmployeeRepository testEmployeeRepository;
    @Mock
    private UserService userService;

    private UUID id;
    private TestEmployeeEntity testEmployeeEntity;

    @BeforeEach
    void setUp(){
        id = UUID.randomUUID();
        testEmployeeEntity = TestEmployeeEntity.builder()
                .id(id)
                .firstName("Test")
                .lastName("Test")
                .position("Testing")
                .department("Testing")
                .build();
    }

    @Test
    void findTestEmployeeEntityById_returnEmployeeEntity() {
        given(testEmployeeRepository.findById((UUID) any())).willReturn(Optional.ofNullable(testEmployeeEntity));
        assertEquals(testEmployeeEntity, testEmployeeService.findTestEmployeeEntityById(id));
    }
    @Test
    void findTestEmployeeEntityById_throwsEmployeeNotFoundException() {
        given(testEmployeeRepository.findById((UUID) any())).willReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> testEmployeeService.findTestEmployeeEntityById(id));
    }

    @Test
    void saveAndConnectWithUser_success() throws IOException {
        UUID userId = UUID.randomUUID();
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id(userId)
                .firstName("Test")
                .lastName("Test")
                .position("Testing")
                .department("Testing")
                .dateOfHire(LocalDate.now())
                .ceo(new byte[]{1, 2, 3})
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .username("Test")
                .password("Test")
                .build();
        TestEmployeeEntity testEmployeeEntity = Converters.employeeDtoToTestEntity(employeeDTO);
        given(userService.addTestEmployeeById(userId, testEmployeeEntity)).willReturn(userEntity);
        given(testEmployeeRepository.save(testEmployeeEntity)).willReturn(testEmployeeEntity);

        TestEmployeeEntity expected = Converters.employeeDtoToTestEntity(employeeDTO);
        expected.setUser(userEntity);

        TestEmployeeEntity actual = testEmployeeService.saveAndConnectWithUser(employeeDTO, userId);
        assertEquals(expected, actual);

    }
}