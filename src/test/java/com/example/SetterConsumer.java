package com.example;

import com.thoughtworks.di.annotation.Inject;

public class SetterConsumer implements ServiceConsumer {
    private Service service;

    @Inject
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String service() {
        return service.service();
    }
}
