package com.thoughtworks.simpleframework.di.core;

public class ConstructorArg<T> {
    private Class<T> type;
    private Object value;

    public ConstructorArg(Class<T> type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
