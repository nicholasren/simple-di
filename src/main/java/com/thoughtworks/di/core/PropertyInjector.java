package com.thoughtworks.di.core;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertyInjector<T> extends DependencyInjector<T> {

    private Map<String, Object> properties = new HashMap<String, Object>();

    public PropertyInjector(Class<T> type) {
        super(type);
    }

    public void property(String name, Object value) {
        properties.put(name, value);
    }

    @Override
    public void inject(T target, Injector injector) {
        if (!properties.isEmpty()) {
            try {
                for (Map.Entry<String, Object> entry : properties.entrySet()) {

                    Field field = type.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(target, entry.getValue());
                }
            } catch (Exception e) {
                throw new BeanCreationException(e);
            }
        }
    }
}
