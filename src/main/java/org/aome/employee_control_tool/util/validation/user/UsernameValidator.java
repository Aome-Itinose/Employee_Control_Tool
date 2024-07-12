package org.aome.employee_control_tool.util.validation.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.services.UserService;

@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {
    private final UserService userService;

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username!=null && !username.isBlank() && !userService.isExistByUsername(username);
    }
}
