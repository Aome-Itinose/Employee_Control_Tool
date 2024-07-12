package org.aome.employee_control_tool.util.converters;

import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;

import java.io.IOException;

public class Converters {
    public static UserEntity authenticationDtoToEntity(AuthenticationDTO dto){
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public static EmployeeEntity employeeDtoToEntity(EmployeeDTO employeeDTO) throws IOException {
        return EmployeeEntity.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .position(employeeDTO.getPosition())
                .department(employeeDTO.getDepartment())
                .dateOfHire(employeeDTO.getDateOfHire())
                .ceoLink(new CEOSaver().saveFileAndReturnPath(employeeDTO.getCeo(), employeeDTO.getFirstName(), employeeDTO.getLastName()))
                .build();
    }

    public static EmployeeDTO employeeEntityToDto(EmployeeEntity employeeEntity){
        return EmployeeDTO.builder()
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .position(employeeEntity.getPosition())
                .department(employeeEntity.getDepartment())
                .dateOfHire(employeeEntity.getDateOfHire())
                .build();
    }
}
