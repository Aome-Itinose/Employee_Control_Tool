package org.aome.employee_control_tool.util.containers;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Data
public class VacationContainer {
    Optional<LocalDate> startAfter;
    Optional<LocalDate> endAfter;
    Optional<LocalDate> startBefore;
    Optional<LocalDate> endBefore;
}
