package com.anotites.pojos.spring;

import org.springframework.stereotype.Component;

@Component("addressnnotationQualifier")
public class AddressFor9 {
    private String city = "London";

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
