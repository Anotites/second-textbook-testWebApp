package com.anotites.pojos.spring;

public class PersonFor7 {
    private String name;
    private AddressFor7 address; // Наша зависимость

    // Конструктор для autowire="constructor"
    public PersonFor7(AddressFor7 address) {
        this.address = address;
    }

    public PersonFor7() {
    } // Пустой конструктор для других типов

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressFor7 address) {
        this.address = address;
    }

    public void printInfo() {
        System.out.println("Name: " + name + ", City: " + (address != null ? address.getCity() : "null"));
    }
}
