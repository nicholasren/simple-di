package com.thoughtworks.di.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;

public class AnnotatedFieldInjector<T> {

    private Class type;
    private Injector injector;

    public AnnotatedFieldInjector(Class type) {
        this.type = type;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public void inject(T target) {
        try {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(javax.inject.Inject.class)) {
                    Object value = injector.get(field.getType());
                    field.setAccessible(true);

                    field.set(target, value);

                }
            }
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
    }
}
