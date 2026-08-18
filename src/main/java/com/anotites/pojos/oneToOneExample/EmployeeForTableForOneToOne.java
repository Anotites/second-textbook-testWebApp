package com.anotites.pojos.oneToOneExample;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EMPLOYEEForOneToOne")
public class EmployeeForTableForOneToOne implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer age;
    @Column
    private String name;
    @Column
    private String surname;
    @OneToOne(mappedBy = "employeeForTableForOneToOne", cascade = CascadeType.ALL)
    @ToString.Exclude
    private EmployeeDetail employeeDetail;
}