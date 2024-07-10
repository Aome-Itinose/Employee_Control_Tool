package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.UserRepository;
import org.aome.employee_control_tool.util.converters.UserConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repository;

    public UserEntity findUserEntityByUsername(String username){
        return repository.findUserEntityByUsername(username).orElseThrow(/*Todo: UserNotFoundException*/);
    }
    public UserEntity findUserEntityById(UUID id){
        return repository.findUserEntityById(id).orElseThrow(/*Todo: UserNotFoundException*/);
    }
    public boolean isExistByUsername(String username){
        return repository.existsByUsername(username);
    }
    @Transactional
    public UserEntity save(AuthenticationDTO authenticationDTO){
        return repository.save(UserConverters.authenticationDtoToEntityConverter(authenticationDTO));
    }
}