package com.thoughtworks.di;

import java.util.LinkedList;
import java.util.List;

public class BindingBuilder<T> {
    private final String name;
    private Class<T> type;
    private final List<Class<?>> constructorArgTypes;
    private final List<Object> constructorArgValues;

    public BindingBuilder(String name) {
        this.name = name;
        this.constructorArgTypes = new LinkedList<Class<?>>();
        this.constructorArgValues = new LinkedList<Object>();
    }

    public BindingBuilder bind(Class<T> clazz) {
        this.type = clazz;
        return this;
    }

    public Binding build() {
        return new Binding(name, type, constructorArgTypes, constructorArgValues);
    }

    public BindingBuilder constructorArg(Class<?> type, Object value) {
        this.constructorArgTypes.add(type);
        this.constructorArgValues.add(value);
        return this;
    }
}
