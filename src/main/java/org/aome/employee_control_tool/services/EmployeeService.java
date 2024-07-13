package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.security.UserDetailsHolder;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.EmployeeRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final TestEmployeeService testEmployeeService;
    private final UserService userService;
    private final UserDetailsHolder userDetailsHolder;
    private final Converters converters;

    public EmployeeEntity findEmployeeEntityById(UUID id){
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }
    public boolean existsById(UUID id){
        return employeeRepository.existsById(id);
    }
    @Transactional
    public EmployeeDTO confirmEmployee(UUID employeeId) {
        TestEmployeeEntity testEmployeeEntity = testEmployeeService.findTestEmployeeEntityById(employeeId);
        EmployeeEntity employeeEntity = Converters.employeeTestEntityToEntity(testEmployeeEntity);

        UUID authorizedUserId = userDetailsHolder.getUserFromSecurityContext().getId();
        UserEntity authorizedUserEntity = userService.setEmployeeById(authorizedUserId, employeeEntity);

        employeeEntity.setUser(authorizedUserEntity);
        testEmployeeService.deleteById(employeeId);
        return Converters.employeeEntityToDto(employeeRepository.save(employeeEntity));
    }
    public boolean existsByFirstNameAndLastName(String firstName, String lastName){
        return employeeRepository.existsByFirstNameAndLastName(firstName, lastName);
    }
}