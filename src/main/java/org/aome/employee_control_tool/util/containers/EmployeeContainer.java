package org.aome.employee_control_tool.util.containers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeContainer {
    Optional<String> firstName;
    Optional<String> lastName;
    Optional<String> position;
    Optional<String> department;
    Optional<LocalDate> dateOfHire;
}
