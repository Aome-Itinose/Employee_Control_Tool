package org.aome.employee_control_tool.security;

import org.aome.employee_control_tool.store.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsHolder {
    public UserEntity getUserFromSecurityContext(){
        return ((UserSecurityDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity();
    }
}
