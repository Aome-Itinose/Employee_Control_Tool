package org.aome.employee_control_tool.util.converters;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CEOSaver {
    private final String CEO_FOLDER = "D:\\Files\\Coding\\Java\\Employee_Control_Tool\\src\\main\\resources\\ceo\\";

    public String saveFileAndReturnPath(byte[] ceo, String firstName, String lastName) throws IOException {
        String fileName = firstName + "_" + lastName;
        File file = new File(CEO_FOLDER + fileName);
        if(!file.exists()){
            try(FileOutputStream writer = new FileOutputStream(file)) {
                writer.write(ceo);
            }
        }
        return file.getPath();
    }
}
