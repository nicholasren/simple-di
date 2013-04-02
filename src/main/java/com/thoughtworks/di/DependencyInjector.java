package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class DependencyInjector<T> {

    private Map<String, String> dependencies = new HashMap<String, String>();
    private Injector injector;
    private final Class<T> type;

    public DependencyInjector(Class type) {
        this.type = type;
    }

    public void depends(String propertyName, String beanName) {
        dependencies.put(propertyName, beanName);
    }

    public void inject(T target) {
        if (!dependencies.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : dependencies.entrySet()) {
                    Object value = injector.get(entry.getValue());
                    Field field = type.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(target, value);
                }
            } catch (Exception e) {
                throw new BeanCreationException(e);
            }
        }
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
