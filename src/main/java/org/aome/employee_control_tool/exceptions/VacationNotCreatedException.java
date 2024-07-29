package org.aome.employee_control_tool.exceptions;

public class VacationNotCreatedException extends RuntimeException{
    public VacationNotCreatedException(){
        super("Vacation not created");
    }
    public VacationNotCreatedException(String message){
        super(message);
    }
}
