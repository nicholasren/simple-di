package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class PropertyInjector<T> {

    private Map<String, Object> properties = new HashMap<String, Object>();
    private final Class<T> type;

    public PropertyInjector(Class<T> type) {
        this.type = type;
    }

    public void property(String name, Object value) {
        properties.put(name, value);
    }

    public void inject(T target) {
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
