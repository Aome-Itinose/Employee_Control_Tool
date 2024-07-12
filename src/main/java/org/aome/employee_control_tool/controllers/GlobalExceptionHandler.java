package org.aome.employee_control_tool.controllers;

import org.aome.employee_control_tool.exceptions.EmployeeNotSaveException;
import org.aome.employee_control_tool.exceptions.UserNotFoundException;
import org.aome.employee_control_tool.responses.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ExceptionResponse exceptionHandler(UserNotFoundException e){
        return new ExceptionResponse(e.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(EmployeeNotSaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ExceptionResponse exceptionHandler(EmployeeNotSaveException e){
        return new ExceptionResponse(e.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    private ExceptionResponse exceptionHandler(AuthenticationException e){
        return new ExceptionResponse(e.getMessage(), LocalDateTime.now());
    }
}
