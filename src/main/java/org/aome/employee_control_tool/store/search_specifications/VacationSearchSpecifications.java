package org.aome.employee_control_tool.store.search_specifications;

import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.VacationEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VacationSearchSpecifications {
    public Specification<VacationEntity> hasVacationStartDateAfter(LocalDate date) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), date));
    }
    public Specification<VacationEntity> hasVacationEndDateBefore(LocalDate date) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), date));
    }
    public Specification<VacationEntity> hasVacationStartDateBefore(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), date);
    }
    public Specification<VacationEntity> hasVacationEndDateAfter(LocalDate date) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), date));
    }
}
