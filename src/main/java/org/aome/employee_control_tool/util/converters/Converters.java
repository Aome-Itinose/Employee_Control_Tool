package org.aome.employee_control_tool.util.converters;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.dtos.TimeSheetDTO;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.TimeSheetEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Converters {

    public static UserEntity authenticationDtoToEntity(AuthenticationDTO dto){
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public static TestEmployeeEntity employeeDtoToTestEntity(EmployeeDTO employeeDTO) throws IOException {
        return TestEmployeeEntity.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .position(employeeDTO.getPosition())
                .department(employeeDTO.getDepartment())
                .dateOfHire(employeeDTO.getDateOfHire())
                .ceoLink(new CEOSaver().saveFileAndReturnPath(employeeDTO.getCeo(), employeeDTO.getFirstName(), employeeDTO.getLastName()))
                .build();
    }
    public static EmployeeEntity employeeTestEntityToEntity(TestEmployeeEntity testEmployeeEntity){
        return EmployeeEntity.builder()
                .firstName(testEmployeeEntity.getFirstName())
                .lastName(testEmployeeEntity.getLastName())
                .position(testEmployeeEntity.getPosition())
                .department(testEmployeeEntity.getDepartment())
                .dateOfHire(testEmployeeEntity.getDateOfHire())
                .ceoLink(testEmployeeEntity.getCeoLink())
                .build();
    }

    public static EmployeeDTO employeeEntityToDto(EmployeeEntity employeeEntity){
        return EmployeeDTO.builder()
                .id(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .position(employeeEntity.getPosition())
                .department(employeeEntity.getDepartment())
                .dateOfHire(employeeEntity.getDateOfHire())
                .build();
    }

    public static TimeSheetDTO timeSheetEntityToDto(TimeSheetEntity timeSheetEntity){
        return TimeSheetDTO.builder()
                .date(timeSheetEntity.getDate())
                .hoursWorked(timeSheetEntity.getHoursWorked())
                .efficiency(timeSheetEntity.getEfficiency())
                .build();
    }
    public static TimeSheetEntity timeSheetDtoToEntity(TimeSheetDTO timeSheetDTO){
        if(timeSheetDTO.getDate() == null){
            timeSheetDTO.setDate(LocalDate.now());
        }
        return TimeSheetEntity.builder()
                .date(timeSheetDTO.getDate())
                .hoursWorked(timeSheetDTO.getHoursWorked())
                .efficiency(timeSheetDTO.getEfficiency())
                .build();
    }
}
