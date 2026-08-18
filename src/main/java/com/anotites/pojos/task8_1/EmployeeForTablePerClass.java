package com.anotites.pojos.task8_1;

import jakarta.persistence.*;
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
@DiscriminatorValue("E")
public class EmployeeForTablePerClass extends PersonForTablePerClass implements Serializable{
    @Serial
    private static final long serialVersionUID = 4L;
    private String company;
    private Double salary;

    public EmployeeForTablePerClass(Integer id, Integer age, String name, String surname, String company, Double salary) {
        super(id, age, name, surname);
        this.company = company;
        this.salary = salary;
    }
}
