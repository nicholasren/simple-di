package com.example;

import javax.inject.Inject;

public class ClassWithAnnotatedField {

    @Inject
    private ClassWithProperty field;

    public ClassWithProperty getField() {
        return field;
    }

    public void setField(ClassWithProperty field) {
        this.field = field;
    }
}
