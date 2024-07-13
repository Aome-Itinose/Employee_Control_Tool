package org.aome.employee_control_tool.exceptions;

public class TimeSheetNotCreatedException extends RuntimeException{
    public TimeSheetNotCreatedException(){
        super("Timesheet not created");
    }
    public TimeSheetNotCreatedException(String message){
        super(message);
    }
}
