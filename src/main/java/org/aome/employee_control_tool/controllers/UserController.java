package org.aome.employee_control_tool.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotSaveException;
import org.aome.employee_control_tool.responses.Response;
import org.aome.employee_control_tool.services.TestEmployeeService;
import org.aome.employee_control_tool.util.ExceptionMessageCollector;
import org.aome.employee_control_tool.util.validation.employee.BecomeEmployeeValidator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final TestEmployeeService testEmployeeService;

    private final BecomeEmployeeValidator becomeEmployeeValidator;

    @PostMapping("/become-employee")
    @ResponseStatus(HttpStatus.OK)
    public Response becomeEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult){
        becomeEmployeeValidator.validate(employeeDTO, bindingResult);
        if(bindingResult.hasErrors()){
            throw new EmployeeNotSaveException(ExceptionMessageCollector.collectMessage(bindingResult));
        }

        testEmployeeService.saveAndConnectWithUser(employeeDTO);

        return new Response("Your request is sent", LocalDateTime.now());
    }

}
