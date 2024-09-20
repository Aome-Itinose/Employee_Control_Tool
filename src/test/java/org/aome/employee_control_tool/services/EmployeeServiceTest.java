package org.aome.employee_control_tool.services;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
        given(employeeRepository.findById((UUID) ArgumentMatchers.any())).willReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeEntityById(UUID.randomUUID()));
    }

    @Test
    void findEmployeeDTOs_returnEmployeeDTOs() {
        EmployeeEntity entity1 = EmployeeEntity.builder()
                .firstName("Alex")
                .lastName("Smith")
                .position("Developer")
                .department("Engineering")
                .dateOfHire(LocalDate.now())
                .build();
        EmployeeEntity entity2 = EmployeeEntity.builder()
                .firstName("Bob")
                .lastName("Smith")
                .position("Engineering")
                .department("Engineering")
                .dateOfHire(LocalDate.now())
                .build();
        List<EmployeeEntity> entities = List.of(entity1, entity2);
        given(employeeRepository.findAll((Specification<EmployeeEntity>) any(), (PageRequest)any())).willReturn(new PageImpl<>(entities));

        EmployeeContainer employeeContainer = EmployeeContainer.builder()
                .firstName(Optional.empty())
                .lastName(Optional.empty())
                .position(Optional.empty())
                .department(Optional.empty())
                .dateOfHire(Optional.empty())
                .build();

        List<EmployeeDTO> expected = entities.stream().map(Converters::employeeEntityToDto).toList();
        List<EmployeeDTO> actual = employeeService.findEmployeeDTOs(employeeContainer, 0,1);

        assertEquals(expected, actual);
    }
    @Test
    void findEmployeeDTOs_throwEmployeeNotFoundException() {
        given(employeeRepository.findAll((Specification<EmployeeEntity>) any(), (PageRequest) any())).willReturn(new PageImpl<>(Collections.emptyList()));

        EmployeeContainer employeeContainer = EmployeeContainer.builder()
                .firstName(Optional.empty())
                .lastName(Optional.empty())
                .position(Optional.empty())
                .department(Optional.empty())
                .dateOfHire(Optional.empty())
                .build();

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeDTOs(employeeContainer, 0,1));
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
    void saveEmployee() {
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

        EmployeeDTO response = employeeService.saveEmployee(id);
        assertEquals(employeeDTO, response);
    }
}