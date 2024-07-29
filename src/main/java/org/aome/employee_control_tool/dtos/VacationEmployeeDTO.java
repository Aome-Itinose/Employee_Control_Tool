package org.aome.employee_control_tool.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacationEmployeeDTO {

    UUID id;

    LocalDate startDate;

    LocalDate endDate;

    EmployeeDTO employee;
}
