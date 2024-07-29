package org.aome.employee_control_tool.store.search_specifications;

import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeSearchSpecifications {
    public Specification<EmployeeEntity> hasFirstNameLike(String firstName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), firstName + "%"));
    }
    public Specification<EmployeeEntity> hasLastNameLike(String lastName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), lastName + "%"));
    }
    public Specification<EmployeeEntity> hasDepartmentNameLike(String departmentName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("department"), departmentName + "%"));
    }
    public Specification<EmployeeEntity> hasPositionNameLike(String positionName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("position"), positionName + "%"));
    }
    public Specification<EmployeeEntity> hasDateOfHireAfter(LocalDate date) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfHire"), date));
    }

}
