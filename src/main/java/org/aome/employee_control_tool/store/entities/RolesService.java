package org.aome.employee_control_tool.store.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RolesService {
    public static String[] roles(Roles role){
        List<String> roles = new ArrayList<>();
        Roles[] possibleRoles = role.getDeclaringClass().getEnumConstants();

        boolean flag = false;
        for(Roles e: possibleRoles){
            if(Objects.equals(e.getTitle(), role.getTitle()) || flag){
                flag = true;
                roles.add(e.getTitle());
            }
        }
        return (String[]) roles.toArray();
    }
}
