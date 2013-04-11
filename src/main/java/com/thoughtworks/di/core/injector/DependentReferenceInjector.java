package com.thoughtworks.di.core.injector;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DependentReferenceInjector<T> extends DependencyInjector<T> {

    private Map<String, String> dependencies = new HashMap<String, String>();

    public DependentReferenceInjector(Class type) {
        super(type);
    }

    public void depends(String propertyName, String beanName) {
        dependencies.put(propertyName, beanName);
    }

    @Override
    public void inject(T target, Injector injector) {
        if (!dependencies.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : dependencies.entrySet()) {
                    Object value = injector.get(entry.getValue(), target.getClass());
                    Field field = type.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(target, value);
                }
            } catch (Exception e) {
                throw new BeanCreationException(e);
            }
        }
    }
}
