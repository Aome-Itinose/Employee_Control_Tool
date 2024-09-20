package org.aome.employee_control_tool.util.containers;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeContainer {
    Optional<String> firstName;
    Optional<String> lastName;
    Optional<String> position;
    Optional<String> department;
    Optional<LocalDate> dateOfHire;
}
