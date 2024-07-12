package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotSaveException;
import org.aome.employee_control_tool.security.UserDetailsHolder;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.EmployeeRepository;
import org.aome.employee_control_tool.store.repositories.UserRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final UserService userService;
    private final UserDetailsHolder userDetailsHolder;

    @Transactional
    public EmployeeDTO saveAndConnectWithUser(EmployeeDTO employeeDTO) {
        try {
            EmployeeEntity employeeEntity = Converters.employeeDtoToEntity(employeeDTO);
            UUID authorizedUserId = userDetailsHolder.getUserFromSecurityContext().getId();
            UserEntity authorizedUserEntity = userService.setEmployeeById(authorizedUserId, employeeEntity);
            employeeEntity.setUser(authorizedUserEntity);
            return Converters.employeeEntityToDto(employeeRepository.save(employeeEntity));
        }catch (IOException e){
            throw new EmployeeNotSaveException(e.getMessage());
        }
    }
}