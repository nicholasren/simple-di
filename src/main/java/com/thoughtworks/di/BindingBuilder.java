package com.thoughtworks.di;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BindingBuilder<T> {
    private String name;
    private Class<T> type;
    private final List<Class<?>> constructorArgTypes;
    private final List<Object> constructorArgValues;
    private Map<String, Object> properties;

    public BindingBuilder(Class<T> type) {
        this.type = type;
        this.constructorArgTypes = new LinkedList<Class<?>>();
        this.constructorArgValues = new LinkedList<Object>();
        this.properties = new HashMap<String, Object>();
    }


    public Binding<T> build() {
        return new Binding<T>(name, type, constructorArgTypes, constructorArgValues, properties);
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

    public BindingBuilder property(String name, Object value) {
        this.properties.put(name, value);
        return this;
    }
}
