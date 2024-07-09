package org.aome.employee_control_tool.store.repositories;

import org.aome.employee_control_tool.store.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntityById(UUID uuid);
}
