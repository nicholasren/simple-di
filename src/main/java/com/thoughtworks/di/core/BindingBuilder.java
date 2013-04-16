package com.thoughtworks.di.core;


import com.thoughtworks.di.core.builder.DefaultTargetBuilder;
import com.thoughtworks.di.core.builder.WithConstructorArgTargetBuilder;

import java.lang.reflect.Constructor;

public class BindingBuilder<T> {
    private Binding<T> binding;

    public BindingBuilder(Class<T> type) {
        if (type.isInterface()) {
            this.binding = new Binding<T>();
            this.binding.setInterfaceClass(type);
        } else {
            this.binding = new Binding<T>(type);
            this.binding.setTargetBuilder(new DefaultTargetBuilder<T>(type));
            this.binding.makeInjectors(type);
        }

    }

    public BindingBuilder<T> withConstructorArg() {
        this.binding.setTargetBuilder(new WithConstructorArgTargetBuilder<T>(this.binding.getType()));
        return this;
    }

    public BindingBuilder<T> constructorArg(Class<?> type, Object value) {
        binding.constructorArg(new ConstructorArg(type, value));
        return this;
    }

    public Binding<T> build() {
        return this.binding;
    }

    public BindingBuilder<T> depends(String propertyName, String beanName) {
        this.binding.depends(propertyName, beanName);
        return this;
    }

    public BindingBuilder<T> to(Class<T> type) {

        if (!hasDefaultConstructor(type)) {
            this.binding.setTargetBuilder(new WithConstructorArgTargetBuilder<T>(type));
        }
        else{
            this.binding.setTargetBuilder(new DefaultTargetBuilder<T>(type));
        }

        this.binding.setType(type);
        this.binding.makeInjectors(type);
        return this;
    }


    public BindingBuilder<T> in(Lifecycle lifecycle) {
        this.binding.setLifecycle(lifecycle);
        return this;
    }

    private boolean hasDefaultConstructor(Class<T> type) {
        return getDefaultConstructor(type) != null;
    }

    private Constructor getDefaultConstructor(Class<T> type) {
        Constructor defaultConstructor = null;
        try {
            defaultConstructor = type.getConstructor(new Class<?>[0]);
        } catch (Exception e) {
        }
        return defaultConstructor;
    }
}
