package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.UserRepository;
import org.aome.employee_control_tool.util.converters.UserConverters;
import org.aome.employee_control_tool.util.exceptions.UserNotFoundException;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserEntity findUserEntityByUsername(String username){
        return repository.findUserEntityByUsername(username).orElseThrow(UserNotFoundException::new);
    }
    public UserEntity findUserEntityById(UUID id){
        return repository.findUserEntityById(id).orElseThrow(UserNotFoundException::new);
    }
    public boolean isExistByUsername(String username){
        return repository.existsByUsername(username);
    }
    @Transactional
    public UserEntity save(AuthenticationDTO authenticationDTO){
        UserEntity userEntity = UserConverters.authenticationDtoToEntityConverter(authenticationDTO);
        return repository.save(enrich(userEntity));
    }

    private UserEntity enrich(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("ROLE_USER");

        return userEntity;
    }
}