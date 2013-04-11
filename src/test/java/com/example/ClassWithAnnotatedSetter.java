package com.example;


import com.thoughtworks.di.annotation.Inject;

public class ClassWithAnnotatedSetter {

    private ClassWithDefaultConstructor field;

    public ClassWithDefaultConstructor getField() {
        return field;
    }

    @Inject
    public void setField(ClassWithDefaultConstructor field) {
        this.field = field;
    }
}
