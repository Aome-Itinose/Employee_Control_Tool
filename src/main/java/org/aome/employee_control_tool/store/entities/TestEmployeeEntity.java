package org.aome.employee_control_tool.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Test_Employee")
public class TestEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "position")
    String position;

    @Column(name = "department")
    String department;

    @Column(name = "date_of_hire")
    LocalDate dateOfHire;

    @Column(name = "ceo_link")
    String ceoLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEmployeeEntity that = (TestEmployeeEntity) o;
        return Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(position, that.position)
                && Objects.equals(department, that.department)
                && Objects.equals(dateOfHire, that.dateOfHire)
                && Objects.equals(ceoLink, that.ceoLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, department, dateOfHire, ceoLink);
    }
}