package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.UserRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.aome.employee_control_tool.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserEntity findUserEntityByUsername(String username){
        return userRepository.findUserEntityByUsername(username).orElseThrow(UserNotFoundException::new);
    }
    public UserEntity findUserEntityById(UUID id){
        return userRepository.findUserEntityById(id).orElseThrow(UserNotFoundException::new);
    }
    public boolean isExistByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public UserEntity save(AuthenticationDTO authenticationDTO){
        UserEntity userEntity = Converters.authenticationDtoToEntity(authenticationDTO);
        return userRepository.save(enrich(userEntity));
    }
    @Transactional
    public UserEntity setEmployeeById(UUID id, EmployeeEntity employeeEntity) throws UserNotFoundException{
        UserEntity user = userRepository.findUserEntityById(id).orElseThrow(UserNotFoundException::new);
        user.setEmployee(employeeEntity);
        user.setRole("ROLE_EMPLOYEE");
        userRepository.save(user);
        return user;
    }
    @Transactional
    public UserEntity addTestEmployeeById(UUID id, TestEmployeeEntity testEmployeeEntity) throws UserNotFoundException{
        UserEntity user = userRepository.findUserEntityById(id).orElseThrow(UserNotFoundException::new);
        user.getTestEmployees().add(testEmployeeEntity);
        userRepository.save(user);
        return user;
    }
    private UserEntity enrich(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("ROLE_USER");

        return userEntity;
    }
}