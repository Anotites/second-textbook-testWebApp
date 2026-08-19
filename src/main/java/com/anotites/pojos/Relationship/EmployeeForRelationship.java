package com.anotites.pojos.Relationship;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EMPLOYEEForOneToOne")
public class EmployeeForRelationship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer age;
    @Column
    private String name;
    @Column
    private String surname;
    @OneToOne(mappedBy = "employeeForRelationship", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeDetail employeeDetail;
    @ManyToOne
    @JoinColumn(name = "f_department_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Department department;
    @ManyToMany(mappedBy = "employees")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Meeting> meetings = new HashSet<>();

    public EmployeeForRelationship(Integer id, Integer age, String name, String surname, EmployeeDetail employeeDetail) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.employeeDetail = employeeDetail;
    }
}