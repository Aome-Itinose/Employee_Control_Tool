package org.aome.employee_control_tool.util.validation.employee;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeExistsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeExistsConstraint {
    String message() default "Employee not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
