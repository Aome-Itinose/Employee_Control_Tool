package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.store.repositories.VacationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional(readOnly = true)
public class VacationService {
    private final VacationRepository repository;
}