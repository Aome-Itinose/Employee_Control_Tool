package org.aome.employee_control_tool.util.validation.employee;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOfHireValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfHireConstraint {
    String message() default "Date of hire invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
