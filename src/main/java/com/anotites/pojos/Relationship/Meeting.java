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
@Table(name = "Meeting")
public class Meeting implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer meetingId;
    @Column
    private String time;
    @ManyToMany
    @JoinTable(
            name = "employee_meeting",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EmployeeForRelationship> employees;
}
