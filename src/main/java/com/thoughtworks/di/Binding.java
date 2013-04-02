package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Binding<T> {
    private String name;
    private Class<T> type;
    private Map<String, Object> properties = new HashMap<String, Object>();
    private TargetBuilder withConstructorArgTargetBuilder;
    private TargetBuilder defaultTargetBuilder;
    private Map<String, String> propertyReferences = new HashMap<String, String>();
    private Injector injector;


    public Binding(Class<T> type) {
        this.type = type;
        this.defaultTargetBuilder = new DefaultTargetBuilder(type);
    }

    public String getName() {
        return name;
    }

    public T getTarget() {

        T target = (T) getTargetBuilder().build();

        injectProperties(target);

        injectDependencies(target);

        return target;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProperties(String name, Object value) {
        this.properties.put(name, value);
    }

    public void addConstructorArg(ConstructorArg arg) {
        if (withConstructorArgTargetBuilder == null) {
            this.withConstructorArgTargetBuilder = new WithConstructorArgTargetBuilder<T>(type);
        }
        ((WithConstructorArgTargetBuilder) this.withConstructorArgTargetBuilder).addConstructorArg(arg);
    }


    public void depends(String propertyName, String beanName) {
        this.propertyReferences.put(propertyName, beanName);
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    private void injectDependencies(T target) {
        if (!propertyReferences.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : propertyReferences.entrySet()) {
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


    private void injectProperties(T target) {
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


    private TargetBuilder getTargetBuilder() {
        return null != withConstructorArgTargetBuilder ? withConstructorArgTargetBuilder : defaultTargetBuilder;
    }
}
