package org.aome.employee_control_tool.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.services.AuthenticationService;
import org.aome.employee_control_tool.util.ExceptionMessageCollector;
import org.aome.employee_control_tool.responses.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationDTO authenticationDTO) throws AuthenticationException {
        String jwtToken = authenticationService.authenticateAndReturnToken(authenticationDTO);

        return new AuthenticationResponse(jwtToken, "Login", LocalDateTime.now());
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registration(@RequestBody @Valid AuthenticationDTO authenticationDTO, BindingResult bindingResult) throws AuthenticationException {
        if(bindingResult.hasErrors()){
            throw new AuthenticationException(ExceptionMessageCollector.collectMessage(bindingResult));
        }
        String jwtToken = authenticationService.saveUserAndReturnToken(authenticationDTO);

        return new AuthenticationResponse(jwtToken, "Registered", LocalDateTime.now());

    }
}
