package com.anotites.pojos.oneToOneExample;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class EmployeeDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 5L;
    @Id
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column
    private String number;
    @Column
    private String street;
    @Column
    private String city;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    private EmployeeForTableForOneToOne employeeForTableForOneToOne;
}
