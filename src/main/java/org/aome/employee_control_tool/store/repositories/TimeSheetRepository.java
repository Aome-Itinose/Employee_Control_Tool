package org.aome.employee_control_tool.store.repositories;

import org.aome.employee_control_tool.store.entities.TimeSheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, Integer> {

}
