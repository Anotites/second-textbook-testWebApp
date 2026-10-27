package com.anotites.pojos.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("personAnnotationQualifier")
public class PersonFor9 {
    private String name;
    @Autowired
    @Qualifier("addressnnotationQualifier") // Явно указываем, какой бин нужен
    private AddressFor9 address; // Наша зависимость

    public PersonFor9() {
    } // Пустой конструктор для других типов

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressFor9 address) {
        this.address = address;
    }

    public void printInfo() {
        System.out.println("Name: " + name + ", City: " + (address != null ? address.getCity() : "null"));
    }
}
