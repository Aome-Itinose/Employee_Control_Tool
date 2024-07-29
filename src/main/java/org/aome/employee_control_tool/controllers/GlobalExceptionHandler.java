package org.aome.employee_control_tool.controllers;

import org.aome.employee_control_tool.exceptions.*;
import org.aome.employee_control_tool.responses.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class, EmployeeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ExceptionResponse notFoundExceptionHandler(RuntimeException e){
        return new ExceptionResponse(e.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler({EmployeeNotCreatedException.class, TimeSheetNotCreatedException.class, VacationNotCreatedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ExceptionResponse notCreatedExceptionHandler(RuntimeException e){
        return new ExceptionResponse(e.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    private ExceptionResponse authExceptionHandler(RuntimeException e){
        return new ExceptionResponse(e.getMessage(), LocalDateTime.now());
    }
}
