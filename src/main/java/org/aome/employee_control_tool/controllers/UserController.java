package org.aome.employee_control_tool.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotCreatedException;
import org.aome.employee_control_tool.responses.Response;
import org.aome.employee_control_tool.security.UserDetailsHolder;
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
    private final UserDetailsHolder userDetailsHolder;

    /**
     * Отправляет запрос на становление работником
     * @param employeeDTO работник
     */
    @PostMapping("/become-employee")
    @ResponseStatus(HttpStatus.CREATED)
    public Response becomeEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult){
        becomeEmployeeValidator.validate(employeeDTO, bindingResult);
        if(bindingResult.hasErrors()){
            throw new EmployeeNotCreatedException(ExceptionMessageCollector.collectMessage(bindingResult));
        }

        testEmployeeService.saveAndConnectWithUser(employeeDTO, userDetailsHolder.getUserFromSecurityContext().getId());

        return new Response("Your request is sent", LocalDateTime.now());
    }


}
