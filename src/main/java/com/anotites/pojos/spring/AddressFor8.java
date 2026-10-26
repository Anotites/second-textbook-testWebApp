package com.anotites.pojos.spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AddressFor8 {
    private String city = "Moscow";

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
