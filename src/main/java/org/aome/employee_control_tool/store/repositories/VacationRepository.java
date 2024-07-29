package org.aome.employee_control_tool.store.repositories;

import org.aome.employee_control_tool.store.entities.VacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRepository extends JpaRepository<VacationEntity, Integer>, JpaSpecificationExecutor<VacationEntity> {

}
