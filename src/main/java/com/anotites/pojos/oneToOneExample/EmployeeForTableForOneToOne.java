package com.anotites.pojos.task8_3;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EMPLOYEEForTablePerConcreteClass")
public class EmployeeForTablePerConcreteClass extends PersonForTablePerConcreteClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private String company;
    private Double salary;

    public EmployeeForTablePerConcreteClass(Integer id, Integer age, String name, String surname, String company, Double salary) {
        super(id, age, name, surname);
        this.company = company;
        this.salary = salary;
    }
}
