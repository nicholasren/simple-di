package com.example;

public class ClassWithReferenceProperty {

    private ClassWithProperty  refProperty;

    public ClassWithProperty getReferenceProperty() {
        return refProperty;
    }

    public void setRefProperty(ClassWithProperty refProperty) {
        this.refProperty = refProperty;
    }
}
