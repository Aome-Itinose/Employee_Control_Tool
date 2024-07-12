package org.aome.employee_control_tool.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.aome.employee_control_tool.util.validation.employee.DateOfHireConstraint;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO {
    @NotBlank
    @JsonProperty("first_name")
    String firstName;

    @NotBlank
    @JsonProperty("last_name")
    String lastName;

    @NotBlank
    @JsonProperty("position")
    String position;

    @NotBlank
    @JsonProperty("department")
    String department;

    @NotNull
    @DateOfHireConstraint
    @JsonProperty("date_of_hire")
    LocalDate dateOfHire;

    @NotNull
    @NotEmpty
    @JsonProperty("ceo")
    byte[] ceo;

}