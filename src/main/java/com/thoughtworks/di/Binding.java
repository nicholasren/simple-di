package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.util.List;

public class Binding<T> {
    private final String name;
    private final Class<T> type;
    private List<Class<?>> constructorArgTypes;
    private List<Object> constructorArgValues;

    public Binding(String name, Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgValues) {
        this.name = name;
        this.type = type;
        this.constructorArgTypes = constructorArgTypes;
        this.constructorArgValues = constructorArgValues;
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
        return target;
    }

}
