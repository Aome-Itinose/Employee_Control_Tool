package org.aome.employee_control_tool.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Users")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "username", unique = true)
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    String role;

    @OneToOne(mappedBy = "user")
    EmployeeEntity employee;

    @OneToMany(mappedBy = "user")
    List<TestEmployeeEntity> testEmployees;

    public List<TestEmployeeEntity> getTestEmployees() {
        return testEmployees == null ? new ArrayList<>() : testEmployees;
    }
}