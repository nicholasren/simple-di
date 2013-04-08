package com.thoughtworks.di.core;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;

public class AnnotatedFieldInjector<T> extends DependencyInjector<T> {

    public AnnotatedFieldInjector(Class type) {
        super(type);
    }

    public void inject(T target, Injector injector) {
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
