package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.util.List;

public class Binding {
    private final String name;
    private final Class<?> type;
    private List<Class<?>> constructorArgTypes;
    private List<Object> constructorArgValues;
    private Object target;

    public Binding(String name, Class<?> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgValues) {
        this.name = name;
        this.type = type;
        this.constructorArgTypes = constructorArgTypes;
        this.constructorArgValues = constructorArgValues;

        try {
            if (this.constructorArgTypes.isEmpty()) {
                this.target = type.newInstance();
            } else {
                Class<?>[] argTypes = {};
                Object[] argValues = {};
                Constructor constructor = type.getConstructor(this.constructorArgTypes.toArray(argTypes));
                this.target = constructor.newInstance(this.constructorArgValues.toArray(argValues));
            }
        } catch (Exception e) {
            System.out.println("exception raised");
            throw new BeanCreationException(e);
        }
    }

    public Object getTarget() {
        return target;
    }

    public String getName() {
        return name;
    }
}
