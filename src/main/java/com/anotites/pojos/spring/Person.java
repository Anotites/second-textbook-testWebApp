package com.anotites.pojos.spring;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Person {
    private String name;
    private List<String> flats;
    private Set<String> surname;
    private Map<String, Integer> exams;
    private Properties books;

    // Spring будет использовать этот сеттер, если мы укажем property в XML
    public void setName(String name) {
        this.name = name;
    }

    public void setFlats(List<String> flats) {
        this.flats = flats;
    }

    public void setSurname(Set<String> surname) {
        this.surname = surname;
    }

    public void setExams(Map<String, Integer> exams) {
        this.exams = exams;
    }

    public void setBooks(Properties books) {
        this.books = books;
    }

    public Map<String, Integer> getExams() {
        return exams;
    }

    public void sayName() {
        System.out.println("Spring says: " + name);
        System.out.println("Flats: " + flats);
        System.out.println("Surnames: " + surname);
        System.out.println("Exams: " + exams);
        System.out.println("Books: " + books);
    }

    public static Person getInstance() {
        return new Person();
    }
}
