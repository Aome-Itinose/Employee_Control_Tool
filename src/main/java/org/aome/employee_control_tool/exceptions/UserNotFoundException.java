package org.aome.employee_control_tool.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User not found");
    }
    public UserNotFoundException(String message){
        super(message);
    }
}
