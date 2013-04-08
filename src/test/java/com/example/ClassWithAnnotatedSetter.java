package com.example;

import javax.inject.Inject;

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
