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
@DiscriminatorValue("S")
public class StudentForTablePerClass extends PersonForTablePerClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private String faculty;
    private Double mark;

    public StudentForTablePerClass(Integer id, Integer age, String name, String surname, String faculty, Double mark) {
        super(id, age, name, surname);
        this.faculty = faculty;
        this.mark = mark;
    }
}
