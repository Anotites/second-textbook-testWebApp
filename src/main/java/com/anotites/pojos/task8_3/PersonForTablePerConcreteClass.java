package com.anotites.pojos.task8_3;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "PERSONForTablePerConcreteClass")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonForTablePerConcreteClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "person_id_gen")
    @TableGenerator(name = "person_id_gen",
            table = "id_generator",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "person_id",
            allocationSize = 1)
    private Integer id;
    @Column
    private Integer age;
    @Column
    private String name;
    @Column
    private String surname;
}
