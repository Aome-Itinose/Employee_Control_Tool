package org.aome.employee_control_tool.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotSaveException;
import org.aome.employee_control_tool.services.EmployeeService;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.responses.Response;
import org.aome.employee_control_tool.util.ExceptionMessageCollector;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmployeeService employeeService;

    @PostMapping("/become-employee")
    public Response becomeEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new EmployeeNotSaveException(ExceptionMessageCollector.collectMessage(bindingResult));
        }

        employeeService.saveAndConnectWithUser(employeeDTO);

        return new Response("Your request is sent", LocalDateTime.now());
    }

}
