package org.aome.employee_control_tool.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.aome.employee_control_tool.util.validation.user.UsernameConstraint;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationDTO {
    @NotBlank
    @UsernameConstraint
    private String username;

    @NotBlank
    private String password;
}
