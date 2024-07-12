package org.aome.employee_control_tool.services;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public String authenticateAndReturnToken(AuthenticationDTO authenticationDTO) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword());

        if(!authenticationManager.authenticate(authToken).isAuthenticated()){
            throw new AuthenticationException();
        }

        return jwtUtil.generateToken(authenticationDTO.getUsername());
    }

    public String saveUserAndReturnToken(AuthenticationDTO authenticationDTO){
        UserEntity userEntity = userService.save(authenticationDTO);

        return jwtUtil.generateToken(userEntity.getUsername());
    }
}