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
}