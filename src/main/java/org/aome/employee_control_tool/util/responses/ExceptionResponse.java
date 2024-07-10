package org.aome.employee_control_tool.util.responses;

import java.time.LocalDateTime;

public class ExceptionResponse extends Response{
    public ExceptionResponse(String message, LocalDateTime time) {
        super(message, time);
    }

    public ExceptionResponse() {
    }
}
