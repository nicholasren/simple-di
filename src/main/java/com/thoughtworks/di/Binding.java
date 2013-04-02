package com.thoughtworks.di;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Binding<T> {
    private String name;
    private Class<T> type;
    private TargetBuilder targetBuilder;
    private PropertyInjector propertyInjector;
    private Map<String, String> dependencies = new HashMap<String, String>();
    private Injector injector;


    public Binding(Class<T> type) {
        this.type = type;
        this.targetBuilder = new DefaultTargetBuilder(type);
        this.propertyInjector = new PropertyInjector(type);
    }

    public String getName() {
        return name;
    }

    public T getTarget() {

        T target = (T) targetBuilder.build();

        propertyInjector.inject(target);

        injectDependencies(target);

        return target;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addConstructorArg(ConstructorArg arg) {
        ((WithConstructorArgTargetBuilder) this.targetBuilder).addConstructorArg(arg);
    }


    public void depends(String propertyName, String beanName) {
        this.dependencies.put(propertyName, beanName);
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    private void injectDependencies(T target) {
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

    public void withConstructorArg() {
        this.targetBuilder = new WithConstructorArgTargetBuilder<T>(type);
    }

    public void addProperties(String name, Object value) {

    }
}
