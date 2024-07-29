package org.aome.employee_control_tool.exceptions;

public class EmployeeNotCreatedException extends RuntimeException{
    public EmployeeNotCreatedException(){
        super("Employee not save");
    }
    public EmployeeNotCreatedException(String message){
        super(message);
    }
}
