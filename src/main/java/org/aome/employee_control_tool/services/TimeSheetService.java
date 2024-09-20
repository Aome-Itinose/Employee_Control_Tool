package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.TimeSheetDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TimeSheetEntity;
import org.aome.employee_control_tool.store.repositories.EmployeeRepository;
import org.aome.employee_control_tool.store.repositories.TimeSheetRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional(readOnly = true)
public class TimeSheetService {
    private final TimeSheetRepository timeSheetRepository;

    private final EmployeeRepository employeeRepository;
    @Transactional
    public TimeSheetDTO saveAndConnectWithEmployee(TimeSheetDTO timeSheetDTO){
        EmployeeEntity employee = employeeRepository.findById(timeSheetDTO.getEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        TimeSheetEntity timeSheetEntity = Converters.timeSheetDtoToEntity(timeSheetDTO);
        timeSheetEntity.setEmployee(employee);
        timeSheetEntity = timeSheetRepository.save(timeSheetEntity);
        employee.getTimeSheets().add(timeSheetEntity);

        return Converters.timeSheetEntityToDto(timeSheetEntity);
    }
}