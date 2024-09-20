package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.exceptions.EmployeeNotCreatedException;
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

    public TestEmployeeEntity findTestEmployeeEntityById(UUID id) throws EmployeeNotFoundException {
        return testEmployeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    @Transactional
    public TestEmployeeEntity saveAndConnectWithUser(EmployeeDTO employeeDTO, UUID userId) throws EmployeeNotCreatedException {
        try {
            TestEmployeeEntity testEmployeeEntity = Converters.employeeDtoToTestEntity(employeeDTO);
            UserEntity authorizedUserEntity = userService.addTestEmployeeById(userId, testEmployeeEntity);
            testEmployeeEntity.setUser(authorizedUserEntity);
            testEmployeeEntity = testEmployeeRepository.save(testEmployeeEntity);
            authorizedUserEntity.getTestEmployees().add(testEmployeeEntity);

            return testEmployeeEntity;
        }catch (IOException e){
            throw new EmployeeNotCreatedException(e.getMessage());
        }

    }
    @Transactional
    public void deleteById(UUID uuid){
        testEmployeeRepository.deleteById(uuid);
    }
}