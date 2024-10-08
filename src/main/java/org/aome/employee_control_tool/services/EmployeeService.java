package org.aome.employee_control_tool.services;

import lombok.Data;
import org.aome.employee_control_tool.exceptions.UserNotFoundException;
import org.aome.employee_control_tool.store.repositories.VacationRepository;
import org.aome.employee_control_tool.store.search_specifications.EmployeeSearchSpecifications;
import org.aome.employee_control_tool.store.search_specifications.VacationSearchSpecifications;
import org.aome.employee_control_tool.util.containers.EmployeeContainer;
import org.aome.employee_control_tool.dtos.EmployeeDTO;
import org.aome.employee_control_tool.exceptions.EmployeeNotFoundException;
import org.aome.employee_control_tool.security.UserDetailsHolder;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.EmployeeRepository;
import org.aome.employee_control_tool.util.converters.Converters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Data
@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final VacationRepository vacationRepository;

    private final TestEmployeeService testEmployeeService;
    private final UserService userService;
    private final UserDetailsHolder userDetailsHolder;
    private final Converters converters;

    private final EmployeeSearchSpecifications employeeSearchSpecifications;
    private final VacationSearchSpecifications vacationSearchSpecifications;

    public EmployeeEntity findEmployeeEntityById(UUID id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    /**
     * Возвращает список работников по заданным в {@link EmployeeContainer} критериям
     */
    public List<EmployeeDTO> findEmployeeDTOs(EmployeeContainer employeeContainer, int page, int pageSize) throws EmployeeNotFoundException {
        Specification<EmployeeEntity> spec = collectSpecification(employeeContainer);

        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("firstName"));

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(spec, pageable).getContent();

        if (employeeEntities.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        return employeeEntities.stream().map(Converters::employeeEntityToDto).toList();
    }


    public boolean existsById(UUID id) {
        return employeeRepository.existsById(id);
    }

    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        return employeeRepository.existsByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Конвертирует [pre-employee -> employee] и сохраняет {@link EmployeeEntity}
     * @return EmployeeDto
     */
    @Transactional
    public EmployeeDTO saveEmployee(UUID employeeId) throws EmployeeNotFoundException, UserNotFoundException {
        TestEmployeeEntity testEmployeeEntity = testEmployeeService.findTestEmployeeEntityById(employeeId);
        EmployeeEntity employeeEntity = Converters.employeeTestEntityToEntity(testEmployeeEntity);

        UserEntity confirmedUser = userService.setEmployeeById(testEmployeeEntity.getUser().getId(), employeeEntity);

        employeeEntity.setUser(confirmedUser);
        employeeEntity = employeeRepository.save(employeeEntity);
        confirmedUser.getTestEmployees().remove(testEmployeeEntity);
        testEmployeeService.deleteById(employeeId);
        return Converters.employeeEntityToDto(employeeEntity);
    }

    private Specification<EmployeeEntity> collectSpecification(EmployeeContainer employeeContainer) {
        Specification<EmployeeEntity> spec = Specification.where(null);

        if (employeeContainer.getFirstName().isPresent()) {
            spec = spec.and(employeeSearchSpecifications.hasFirstNameLike(employeeContainer.getFirstName().get()));
        }
        if (employeeContainer.getLastName().isPresent()) {
            spec = spec.and(employeeSearchSpecifications.hasLastNameLike(employeeContainer.getLastName().get()));
        }
        if (employeeContainer.getPosition().isPresent()) {
            spec = spec.and(employeeSearchSpecifications.hasPositionNameLike(employeeContainer.getPosition().get()));
        }
        if (employeeContainer.getDepartment().isPresent()) {
            spec = spec.and(employeeSearchSpecifications.hasDepartmentNameLike(employeeContainer.getDepartment().get()));
        }
        if (employeeContainer.getDateOfHire().isPresent()) {
            spec = spec.and(employeeSearchSpecifications.hasDateOfHireAfter(employeeContainer.getDateOfHire().get()));
        }
        return spec;
    }
}