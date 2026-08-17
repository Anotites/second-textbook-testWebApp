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
@Table(name = "STUDENTForTablePerSubClass")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class StudentForTablePerSubClass extends PersonForTablePerSubClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private String faculty;
    private Double mark;

    public StudentForTablePerSubClass(Integer id, Integer age, String name, String surname, String faculty, Double mark) {
        super(id, age, name, surname);
        this.faculty = faculty;
        this.mark = mark;
    }
}
