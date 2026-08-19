package com.anotites.pojos.Relationship;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "Department")
public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;
    @Column
    private String departmentName;
    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EmployeeForRelationship> employees;
}

