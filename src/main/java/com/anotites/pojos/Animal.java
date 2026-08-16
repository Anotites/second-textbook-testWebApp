package com.anotites.pojos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Access(AccessType.FIELD) // по умолчанию все поля через FIELD
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;
    private Integer age;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'default_name'")
    private String nickName;
    @Column(nullable = false)
    private String type;

    public Animal(Integer number, Integer age, String nickName, String type) {
        this.number = number;
        this.age = age;
        this.nickName = nickName;
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "animal_age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
