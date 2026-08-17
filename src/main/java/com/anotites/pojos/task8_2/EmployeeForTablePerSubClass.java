package com.anotites.pojos.task8_2;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "EMPLOYEEForTablePerSubClass")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class EmployeeForTablePerSubClass extends PersonForTablePerSubClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private String company;
    private Double salary;

    public EmployeeForTablePerSubClass(Integer id, Integer age, String name, String surname, String company, Double salary) {
        super(id, age, name, surname);
        this.company = company;
        this.salary = salary;
    }
}
