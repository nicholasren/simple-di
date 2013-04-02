package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

class DefaultTargetBuilder<T> implements TargetBuilder<T> {

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
}
