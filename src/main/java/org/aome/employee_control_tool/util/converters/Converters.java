package org.aome.employee_control_tool.util.converters;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.*;
import org.aome.employee_control_tool.store.entities.*;
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
    public static VacationEntity vacationDtoToEntity(VacationDTO vacationDTO){
        return VacationEntity.builder()
                .startDate(vacationDTO.getStartDate())
                .endDate(vacationDTO.getEndDate())
                .build();
    }
    public static VacationDTO vacationEntityToDto(VacationEntity vacationEntity){
        return VacationDTO.builder()
                .startDate(vacationEntity.getStartDate())
                .endDate(vacationEntity.getEndDate())
                .build();
    }
    public static VacationEmployeeDTO vacationEntityToVacationEmployeeDTO(VacationEntity vacationEntity){
        return VacationEmployeeDTO.builder()
                .id(vacationEntity.getId())
                .startDate(vacationEntity.getStartDate())
                .endDate(vacationEntity.getEndDate())
                .employee(Converters.employeeEntityToDto(vacationEntity.getEmployee()))
                .build();
    }
}
