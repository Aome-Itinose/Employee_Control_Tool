package org.aome.employee_control_tool.util.converters;

import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.store.entities.UserEntity;

public class UserConverters {
    public static UserEntity authenticationDtoToEntityConverter(AuthenticationDTO dto){
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
