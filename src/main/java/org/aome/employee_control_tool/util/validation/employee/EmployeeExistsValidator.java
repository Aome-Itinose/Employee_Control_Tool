package org.aome.employee_control_tool.util.validation.employee;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.services.EmployeeService;

import java.util.UUID;

@RequiredArgsConstructor
public class EmployeeExistsValidator implements ConstraintValidator<EmployeeExistsConstraint, UUID> {
    private final EmployeeService employeeService;
    @Override
    public void initialize(EmployeeExistsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        return employeeService.existsById(uuid);
    }
}
