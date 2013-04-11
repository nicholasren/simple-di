package com.example;


import com.thoughtworks.di.annotation.Inject;

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
