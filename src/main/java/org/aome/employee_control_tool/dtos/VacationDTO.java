package org.aome.employee_control_tool.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("employee_id")
    UUID employeeId;

    @JsonProperty("start_date")
    @NotNull
    LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull
    LocalDate endDate;

}