package org.aome.employee_control_tool.util.validation.employee;

import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.services.EmployeeService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BecomeEmployeeValidator implements Validator {
    private final EmployeeService employeeService;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(EmployeeDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employeeDTO = (EmployeeDTO) target;
        if(employeeService.existsByFirstNameAndLastName(employeeDTO.getFirstName(), employeeDTO.getLastName())){
            String message = String.format("Employee %s %s id already exist", employeeDTO.getFirstName(), employeeDTO.getLastName());
            errors.rejectValue("firstName", "", message);
        }
    }
}
