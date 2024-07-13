package org.aome.employee_control_tool.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Time_Sheet")
public class TimeSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    EmployeeEntity employee;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "hours_worked")
    Integer hoursWorked;

    @Column(name = "efficiency")
    Double efficiency;
}