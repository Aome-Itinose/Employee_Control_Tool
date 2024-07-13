package org.aome.employee_control_tool.store.repositories;

import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TestEmployeeRepository extends JpaRepository<TestEmployeeEntity, Integer> {
   Optional<TestEmployeeEntity> findById(UUID id);
   void deleteById(UUID uuid);

}
