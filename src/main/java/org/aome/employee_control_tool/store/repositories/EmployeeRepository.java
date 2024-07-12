package org.aome.employee_control_tool.store.repositories;

import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
