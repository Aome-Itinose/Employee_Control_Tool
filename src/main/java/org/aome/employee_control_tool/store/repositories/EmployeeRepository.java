package org.aome.employee_control_tool.store.repositories;

import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>, JpaSpecificationExecutor<EmployeeEntity> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    boolean existsById(UUID id);
    Optional<EmployeeEntity> findById(UUID id);
}
