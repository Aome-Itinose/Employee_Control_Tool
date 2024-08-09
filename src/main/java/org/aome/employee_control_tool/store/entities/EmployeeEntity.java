package org.aome.employee_control_tool.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
@Table(name = "Employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne
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

    @OneToMany(mappedBy = "employee")
    List<TimeSheetEntity> timeSheets;

    @OneToMany(mappedBy = "employee")
    List<VacationEntity> vacations;

    public List<TimeSheetEntity> getTimeSheets() {
        if(timeSheets == null) timeSheets = new ArrayList<>();
        return timeSheets;
    }

    public List<VacationEntity> getVacations() {
        if(vacations == null) vacations = new ArrayList<>();
        return vacations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity entity = (EmployeeEntity) o;
        return Objects.equals(firstName, entity.firstName)
                && Objects.equals(lastName, entity.lastName)
                && Objects.equals(position, entity.position)
                && Objects.equals(department, entity.department)
                && Objects.equals(dateOfHire, entity.dateOfHire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, department, dateOfHire);
    }
}