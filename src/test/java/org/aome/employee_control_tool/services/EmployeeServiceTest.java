package org.aome.employee_control_tool.services;

import jakarta.el.MethodNotFoundException;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.security.UserDetailsHolder;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.EmployeeRepository;
import org.aome.employee_control_tool.store.repositories.VacationRepository;
import org.aome.employee_control_tool.store.search_specifications.EmployeeSearchSpecifications;
import org.aome.employee_control_tool.store.search_specifications.VacationSearchSpecifications;
import org.aome.employee_control_tool.util.containers.EmployeeContainer;
import org.aome.employee_control_tool.util.converters.Converters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private VacationRepository vacationRepository;
    @Mock
    private TestEmployeeService testEmployeeService;
    @Mock
    private UserService userService;
    @Mock
    private UserDetailsHolder userDetailsHolder;
    @Mock
    private Converters converters;
    @Mock
    private EmployeeSearchSpecifications employeeSearchSpecifications;
    @Mock
    private VacationSearchSpecifications vacationSearchSpecifications;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findEmployeeEntityById_returnEmployeeEntity() {
        EmployeeEntity entity = new EmployeeEntity();
        given(employeeRepository.findById((UUID) ArgumentMatchers.any())).willReturn(Optional.of(entity));
        assertEquals(entity, employeeService.findEmployeeEntityById(UUID.randomUUID()));
    }
    @Test
    void findEmployeeEntityById_throwException() {
        EmployeeEntity entity = new EmployeeEntity();
        given(employeeRepository.findById((UUID) ArgumentMatchers.any())).willReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeEntityById(UUID.randomUUID()));
    }

    @Test
    void findEmployeeDTOs() {

    }

    @Test
    void existsById_returnTrue() {
        given(employeeRepository.existsById((UUID) any())).willReturn(true);
        assertTrue(employeeService.existsById(UUID.randomUUID()));
    }
    @Test
    void existsById_returnFalse() {
        given(employeeRepository.existsById((UUID) any())).willReturn(false);
        assertFalse(employeeService.existsById(UUID.randomUUID()));
    }

    @Test
    void existsByFirstNameAndLastName_returnTrue() {
        given(employeeRepository.existsById((UUID) any())).willReturn(true);
        assertTrue(employeeService.existsById(UUID.randomUUID()));
    }
    @Test
    void existsByFirstNameAndLastName_returnFalse() {
        given(employeeRepository.existsById((UUID) any())).willReturn(false);
        assertFalse(employeeService.existsById(UUID.randomUUID()));
    }

    @Test
    void confirmEmployee() {
        UUID id = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("test")
                .password("test")
                .build();
        TestEmployeeEntity testEmployeeEntity = TestEmployeeEntity.builder()
                .id(id)
                .user(userEntity)
                .firstName("Test")
                .lastName("Test")
                .position("Testing")
                .department("Testings")
                .dateOfHire(LocalDate.now())
                .ceoLink("someLink")
                .build();
        EmployeeEntity employeeEntity = Converters.employeeTestEntityToEntity(testEmployeeEntity);
        EmployeeDTO employeeDTO = Converters.employeeEntityToDto(employeeEntity);

        given(testEmployeeService.findTestEmployeeEntityById(any())).willReturn(testEmployeeEntity);
        given(userService.setEmployeeById(any(), any())).willReturn(userEntity);
        given(employeeRepository.save(any())).willReturn(employeeEntity);

        EmployeeDTO response = employeeService.confirmEmployee(id);
        assertEquals(employeeDTO, response);
    }
}