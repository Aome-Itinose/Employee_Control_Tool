package org.aome.employee_control_tool.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.aome.employee_control_tool.util.validation.UsernameConstraint;

@Data
public class AuthenticationDTO {
    @NotBlank
    @UsernameConstraint
    private String username;

    @NotBlank
    private String password;
}
