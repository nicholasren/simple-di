package com.thoughtworks.di.core.builder;

import com.thoughtworks.di.core.ConstructorArg;
import com.thoughtworks.di.exception.BeanCreationException;

public class DefaultTargetBuilder<T> implements TargetBuilder<T> {

    private Class type;

    public DefaultTargetBuilder(Class type) {
        this.type = type;
    }

    public T build() {
        try {
            return (T) this.type.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
    }

    @Override
    public void constructorArg(ConstructorArg arg) {
    }
}
