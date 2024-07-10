package org.aome.employee_control_tool.util.responses;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message;
    private LocalDateTime time;
}
