package com.thoughtworks.di;

import java.util.LinkedList;
import java.util.List;

public class BindingBuilder<T> {
    private String name;
    private Class<T> type;
    private final List<Class<?>> constructorArgTypes;
    private final List<Object> constructorArgValues;

    public BindingBuilder(Class<T> type) {
        this.type = type;
        this.constructorArgTypes = new LinkedList<Class<?>>();
        this.constructorArgValues = new LinkedList<Object>();
    }


    public Binding<T> build() {
        return new Binding<T>(name, type, constructorArgTypes, constructorArgValues);
    }

    public BindingBuilder<T> constructorArg(Class<?> type, Object value) {
        this.constructorArgTypes.add(type);
        this.constructorArgValues.add(value);
        return this;
    }

    public BindingBuilder<T> to(String name) {
        this.name = name;
        return this;
    }
}
