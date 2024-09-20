package org.aome.employee_control_tool.services;

import org.aome.employee_control_tool.dtos.TimeSheetDTO;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TimeSheetEntity;
import org.aome.employee_control_tool.store.repositories.EmployeeRepository;
import org.aome.employee_control_tool.store.repositories.TimeSheetRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TimeSheetServiceTest {

    @InjectMocks
    private TimeSheetService timeSheetService;

    @Mock
    private TimeSheetRepository timeSheetRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void saveAndConnectWithEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Test")
                .lastName("Test")
                .department("Test")
                .position("Test")
                .dateOfHire(LocalDate.now())
                .build();
        TimeSheetDTO timeSheetDTO = TimeSheetDTO.builder()
                .employeeId(employeeEntity.getId())
                .date(LocalDate.now())
                .hoursWorked(1)
                .efficiency(0.0)
                .build();
        given(employeeRepository.findById(employeeEntity.getId())).willReturn(Optional.of(employeeEntity));
        TimeSheetEntity timeSheetEntity = Converters.timeSheetDtoToEntity(timeSheetDTO);

        timeSheetEntity.setEmployee(employeeEntity);
        TimeSheetDTO expected = Converters.timeSheetEntityToDto(timeSheetEntity);
        given(timeSheetRepository.save(any())).willReturn(timeSheetEntity);
        TimeSheetDTO actual = timeSheetService.saveAndConnectWithEmployee(timeSheetDTO);
        assertEquals(expected, actual);
    }
}