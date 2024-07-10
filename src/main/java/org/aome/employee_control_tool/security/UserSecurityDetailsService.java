package org.aome.employee_control_tool.security;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSecurityDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserSecurityDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findUserEntityByUsername(username);
        return new UserSecurityDetails(userEntity);
    }
}
