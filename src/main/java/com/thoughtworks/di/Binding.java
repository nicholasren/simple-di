package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Binding<T> {
    private final String name;
    private final Class<T> type;
    private List<Class<?>> constructorArgTypes;
    private List<Object> constructorArgValues;
    private final Map<String, Object> properties;

    public Binding(String name, Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgValues, Map<String, Object> properties) {
        this.name = name;
        this.type = type;
        this.constructorArgTypes = constructorArgTypes;
        this.constructorArgValues = constructorArgValues;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public T getTarget() {
        return buildTarget();
    }


    private T buildTarget() {
        T target;
        try {
            if (this.constructorArgTypes.isEmpty()) {
                target = this.type.newInstance();
            } else {
                Class<?>[] argTypes = {};
                Object[] argValues = {};
                Constructor constructor = this.type.getConstructor(this.constructorArgTypes.toArray(argTypes));
                target = (T) constructor.newInstance(this.constructorArgValues.toArray(argValues));
            }
        } catch (Exception e) {
            System.out.println("exception raised");
            throw new BeanCreationException(e);
        }

        injectProperties(target);
        return target;
    }

    private void injectProperties(T target) {
        if (!properties.isEmpty()) {
            try {
                for (Map.Entry<String, Object> entry : properties.entrySet()) {

                    Field field = type.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(target, entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
