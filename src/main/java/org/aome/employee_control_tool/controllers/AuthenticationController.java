package org.aome.employee_control_tool.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.util.exceptions.ExceptionMessageCollector;
import org.aome.employee_control_tool.util.responses.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationDTO authenticationDTO) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword());

        if(!authenticationManager.authenticate(authToken).isAuthenticated()){
            throw new AuthenticationException();
        }

        String jwtToken = jwtUtil.generateToken(authenticationDTO.getUsername());
        return new AuthenticationResponse(jwtToken, "Login", LocalDateTime.now());
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registration(@RequestBody @Valid AuthenticationDTO authenticationDTO, BindingResult bindingResult) throws AuthenticationException {
        String exceptionMessage = ExceptionMessageCollector.collectMessageAndThrowException(bindingResult);
        if(exceptionMessage != null){
            throw new AuthenticationException(exceptionMessage);
        }

        UserEntity userEntity = userService.save(authenticationDTO);

        String jwtToken = jwtUtil.generateToken(userEntity.getUsername());
        return new AuthenticationResponse(jwtToken, "Registered", LocalDateTime.now());

    }
}
