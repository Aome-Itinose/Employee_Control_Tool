package org.aome.employee_control_tool.util.validation.employee;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class DateOfHireValidation implements ConstraintValidator<DateOfHireConstraint, LocalDate> {
    private final LocalDate MIN = LocalDate.of(1970, Month.JANUARY, 1);

    @Override
    public void initialize(DateOfHireConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation); //Todo: check
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.MIN) && localDate.isBefore(LocalDate.now().plusDays(1));
    }
}
