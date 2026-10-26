package com.anotites.pojos.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class PersonFor8 {
    private String name;
    private AddressFor8 address; // Наша зависимость

    @Autowired(required = false)
    public PersonFor8(AddressFor8 address) {
        this.address = address;
    }

    public PersonFor8() {
    } // Пустой конструктор для других типов

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressFor8 address) {
        this.address = address;
    }

    public void printInfo() {
        System.out.println("Name: " + name + ", City: " + (address != null ? address.getCity() : "null"));
    }
}
