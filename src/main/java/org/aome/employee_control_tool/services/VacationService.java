package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.dtos.VacationDTO;
import org.aome.employee_control_tool.dtos.VacationEmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.VacationEntity;
import org.aome.employee_control_tool.store.repositories.VacationRepository;
import org.aome.employee_control_tool.store.search_specifications.VacationSearchSpecifications;
import org.aome.employee_control_tool.util.containers.VacationContainer;
import org.aome.employee_control_tool.util.converters.Converters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
@Transactional(readOnly = true)
public class VacationService {
    private final VacationRepository vacationRepository;

    private final EmployeeService employeeService;
    private final VacationSearchSpecifications vacationSearchSpecifications;

    public List<VacationEmployeeDTO> findVacationDTOs(VacationContainer vacationContainer, int page, int pageSize) {
        Specification<VacationEntity> specification = collectSpecification(vacationContainer);
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("startDate"));

        List<VacationEntity> vacations = vacationRepository.findAll(specification, pageable).getContent();
        if (vacations.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return vacations.stream().map(Converters::vacationEntityToVacationEmployeeDTO).toList();

    }

    @Transactional
    public VacationDTO saveAndConnectWithEmployee(VacationDTO vacationDTO){
        EmployeeEntity employee = employeeService.findEmployeeEntityById(vacationDTO.getEmployeeId());
        VacationEntity vacation = Converters.vacationDtoToEntity(vacationDTO);
        vacation.setEmployee(employee);
        employee.getVacations().add(vacationRepository.save(vacation));

        return Converters.vacationEntityToDto(vacation);
    }

    private Specification<VacationEntity> collectSpecification(VacationContainer vacationContainer) {
        Specification<VacationEntity> spec = Specification.where(null);
        if(vacationContainer.getStartAfter().isPresent()){
            spec = spec.and(vacationSearchSpecifications.hasVacationStartDateAfter(vacationContainer.getStartAfter().get()));
        }
        if(vacationContainer.getStartBefore().isPresent()){
            spec = spec.and(vacationSearchSpecifications.hasVacationStartDateBefore(vacationContainer.getStartBefore().get()));
        }
        if(vacationContainer.getEndAfter().isPresent()){
            spec = spec.and(vacationSearchSpecifications.hasVacationEndDateAfter(vacationContainer.getEndAfter().get()));
        }
        if(vacationContainer.getEndBefore().isPresent()){
            spec = spec.and(vacationSearchSpecifications.hasVacationEndDateBefore(vacationContainer.getEndBefore().get()));
        }
        return spec;
    }

}