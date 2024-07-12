package org.aome.employee_control_tool.store.entities;

import lombok.Getter;

@Getter
public enum Roles {
    USER("USER"),
    EMPLOYEE("EMPLOYEE"),
    TEAMLEAD("TEAMLEAD"),
    DIRECTOR("DIRECTOR"),
    BIG_BROTHER("BIG_BROTHER");

    private final String title;
    Roles(String title){
        this.title = title;
    }
}


