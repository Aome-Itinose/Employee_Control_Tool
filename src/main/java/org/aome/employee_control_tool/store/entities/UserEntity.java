package org.aome.employee_control_tool.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }
}