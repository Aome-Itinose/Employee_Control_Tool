package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.exceptions.EmployeeNotSaveException;
import org.aome.employee_control_tool.security.UserDetailsHolder;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.TestEmployeeRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class TestEmployeeService {
    private final TestEmployeeRepository testEmployeeRepository;

    private final UserService userService;
    private final UserDetailsHolder userDetailsHolder;
    private final Converters converters;

    public TestEmployeeEntity findTestEmployeeEntityById(UUID id){
        return testEmployeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }
    @Transactional
    public void saveAndConnectWithUser(EmployeeDTO employeeDTO) {
        try {
            TestEmployeeEntity testEmployeeEntity = Converters.employeeDtoToTestEntity(employeeDTO);
            UUID authorizedUserId = userDetailsHolder.getUserFromSecurityContext().getId();
            UserEntity authorizedUserEntity = userService.addTestEmployeeById(authorizedUserId, testEmployeeEntity);
            testEmployeeEntity.setUser(authorizedUserEntity);
            testEmployeeRepository.save(testEmployeeEntity);
        }catch (IOException e){
            throw new EmployeeNotSaveException(e.getMessage());
        }
    }
    @Transactional
    public void deleteById(UUID uuid){
        testEmployeeRepository.deleteById(uuid);
    }
}