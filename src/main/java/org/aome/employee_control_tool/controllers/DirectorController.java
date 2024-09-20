package org.aome.employee_control_tool.controllers;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.dtos.IdDTO;
import org.aome.employee_control_tool.responses.Response;
import org.aome.employee_control_tool.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/director")
public class DirectorController {

    private final EmployeeService employeeService;


    /**
     * Подтверждение работника [pre-employee -> employee]
     * @param testEmployeeId pre-employee id entity
     */
    @PostMapping("/confirm-employee")
    @ResponseStatus(HttpStatus.OK)
    public Response confirmEmployee(@RequestBody IdDTO testEmployeeId){
        EmployeeDTO employeeDTO = employeeService.saveEmployee(testEmployeeId.getId());

        String message = String.format("Now %s %s is your employee", employeeDTO.getFirstName(), employeeDTO.getLastName());
        return new Response(message, LocalDateTime.now());
    }
}
