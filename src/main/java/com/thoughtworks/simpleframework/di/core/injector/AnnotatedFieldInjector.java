package com.thoughtworks.simpleframework.di.core.injector;

import com.thoughtworks.simpleframework.di.annotation.Inject;
import com.thoughtworks.simpleframework.di.core.Injector;
import com.thoughtworks.simpleframework.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedFieldInjector<T> extends DependencyInjector<T> {

    private List<Field> annotatedFields = new ArrayList<Field>();

    public AnnotatedFieldInjector(Class type) {
        super(type);
        extractAnnotatedFields();
    }

    public void inject(T target, Injector injector) {
        for (Field field : annotatedFields) {
            Object value = injector.get(field.getType());
            setValue(target, field, value);
        }
    }

    private void setValue(T target, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(target, value);
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
    }

    private void extractAnnotatedFields() {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                annotatedFields.add(field);
            }
        }
    }
}
