package org.aome.employee_control_tool.exceptions;

public class EmployeeNotSaveException extends RuntimeException{
    public EmployeeNotSaveException(){
        super("Employee not save");
    }
    public EmployeeNotSaveException(String message){
        super(message);
    }
}
