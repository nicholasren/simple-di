package com.example;

import javax.inject.Inject;

public class User {
    private String name;
    private Phone phone;

    @Inject
    private Service service;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Service getService() {
        return service;
    }


    public void setService(Service service) {
        this.service = service;
    }
}
