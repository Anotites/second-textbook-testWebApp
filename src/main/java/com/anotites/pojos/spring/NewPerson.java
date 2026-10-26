package com.anotites.pojos.spring;

public class NewPerson {
    String name;

    public NewPerson(String name) {
        this.name = name;
    }

    public NewPerson() {
    }

    // Spring будет использовать этот сеттер, если мы укажем property в XML
    public void setName(String name) {
        this.name = name;
    }

    public void sayName() {
        System.out.println("Spring says: " + name);
    }

    public static NewPerson getInstance(String name) {
        return new NewPerson(name);
    }
}
