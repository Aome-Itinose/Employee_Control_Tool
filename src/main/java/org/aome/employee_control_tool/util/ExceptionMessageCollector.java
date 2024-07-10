package org.aome.employee_control_tool.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ExceptionMessageCollector {
    public static String collectMessageAndThrowException(BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            StringBuilder message = new StringBuilder();
            for(FieldError err: bindingResult.getFieldErrors()){
                message.append(err.getField()).append(": ").append(err.getDefaultMessage()).append("; ");
            }
            return message.toString();
        }
        return null;
    }
}
