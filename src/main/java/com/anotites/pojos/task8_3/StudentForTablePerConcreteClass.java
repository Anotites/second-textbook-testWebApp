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
@Table(name = "STUDENTForTablePerConcreteClass")
public class StudentForTablePerConcreteClass extends PersonForTablePerConcreteClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private String faculty;
    private Double mark;

    public StudentForTablePerConcreteClass(Integer id, Integer age, String name, String surname, String faculty, Double mark) {
        super(id, age, name, surname);
        this.faculty = faculty;
        this.mark = mark;
    }
}
