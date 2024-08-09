package org.aome.employee_control_tool.controllers;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.dtos.VacationEmployeeDTO;
import org.aome.employee_control_tool.services.EmployeeService;
import org.aome.employee_control_tool.services.VacationService;
import org.aome.employee_control_tool.util.containers.EmployeeContainer;
import org.aome.employee_control_tool.util.containers.VacationContainer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/employee")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final VacationService vacationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> getEmployees(@RequestParam(required = false, name = "first_name") Optional<String> firstName,
                                          @RequestParam(required = false, name = "last_name") Optional<String> lastName,
                                          @RequestParam(required = false) Optional<String> position,
                                          @RequestParam(required = false) Optional<String> department,
                                          @RequestParam(required = false, name = "date_of_hire") Optional<LocalDate> dateOfHire,
                                          @RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size){

        EmployeeContainer employee = EmployeeContainer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .position(position)
                .department(department)
                .dateOfHire(dateOfHire)
                .build();

        return employeeService.findEmployeeDTOs(employee, page, size);
    }

    @GetMapping("/by-vacation")
    @ResponseStatus(HttpStatus.OK)
    public List<VacationEmployeeDTO> getEmployeeByVacation(@RequestParam(required = false) Optional<LocalDate> startAfter,
                                                           @RequestParam(required = false) Optional<LocalDate> startBefore,
                                                           @RequestParam(required = false) Optional<LocalDate> endAfter,
                                                           @RequestParam(required = false) Optional<LocalDate> endBefore,
                                                           @RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size){
        VacationContainer vacation = VacationContainer.builder()
                .startAfter(startAfter)
                .startBefore(startBefore)
                .endAfter(endAfter)
                .endBefore(endBefore)
                .build();
        return vacationService.findVacationDTOs(vacation, page, size);
    }

}
