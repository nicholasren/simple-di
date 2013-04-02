package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Binding<T> {
    private String name;
    private Class<T> type;
    private List<Class<?>> constructorArgTypes = new ArrayList<Class<?>>();
    private List<Object> constructorArgValues = new ArrayList<Object>();
    private Map<String, Object> properties = new HashMap<String, Object>();

    public Binding() {

    }

    public String getName() {
        return name;
    }

    public T getTarget() {
        return buildTarget();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void addConstructorArgTypes(Class<?> constructorArgTypes) {
        this.constructorArgTypes.add(constructorArgTypes);
    }

    public void addConstructorArgValues(Object constructorArgValues) {
        this.constructorArgValues.add(constructorArgValues);
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

    public void addProperties(String name, Object value) {
        this.properties.put(name, value);
    }
}
