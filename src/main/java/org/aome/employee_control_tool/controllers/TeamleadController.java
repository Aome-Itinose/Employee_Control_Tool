package org.aome.employee_control_tool.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.dtos.TimeSheetDTO;
import org.aome.employee_control_tool.exceptions.TimeSheetNotCreatedException;
import org.aome.employee_control_tool.services.TimeSheetService;
import org.aome.employee_control_tool.util.ExceptionMessageCollector;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teamlead")
public class TeamleadController {
    private final TimeSheetService timeSheetService;
    @PostMapping("/add-timesheet")
    @ResponseStatus(HttpStatus.OK)
    public void addTimeSheet(@RequestBody @Valid TimeSheetDTO timeSheetDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new TimeSheetNotCreatedException(ExceptionMessageCollector.collectMessage(bindingResult));
        }
        timeSheetService.saveAndConnectWithEmployee(timeSheetDTO);
    }
}
