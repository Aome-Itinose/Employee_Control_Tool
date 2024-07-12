package org.aome.employee_control_tool.responses;

import java.time.LocalDateTime;

public class ExceptionResponse extends Response{
    public ExceptionResponse(String message, LocalDateTime time) {
        super(message, time);
    }

    public ExceptionResponse() {
    }
}
