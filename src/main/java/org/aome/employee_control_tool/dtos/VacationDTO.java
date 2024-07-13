package org.aome.employee_control_tool.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.aome.employee_control_tool.util.validation.employee.EmployeeExistsConstraint;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class VacationDTO {
    @NotNull
    @EmployeeExistsConstraint
    UUID employeeId;

    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

}