package com.example.lab7.models;


import com.example.lab7.enums.EmployeeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "employee")
@NamedQueries(
        @NamedQuery(name = "Employee.findAll", query = "select e from Employee e where e.status= ?1")
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long id;
    @Column(name = "full_name", length = 150, nullable = false)
    private String fullname;
    @Column(name = "dob", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "mm-DD-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dob;
    @Column(name = "email", unique = true, length = 150)
    private String email;


    @Column(name = "phone", length = 15, nullable = false)
    private String phone;
    @Column(name = "address", length = 250, nullable = false)
    private String address;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EmployeeStatus status;

    public Employee(long id) {
        this.id = id;
    }
}
