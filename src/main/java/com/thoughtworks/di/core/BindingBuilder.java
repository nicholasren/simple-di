package com.thoughtworks.di.core;

import com.example.ImplementationOfAInterface;

public class BindingBuilder<T> {
    private Binding<T> binding;

    public BindingBuilder(Class<T> type) {
        this.binding = new Binding<T>(type);
    }

    public BindingBuilder<T> withConstructorArg() {
        this.binding.setTargetBuilder(new WithConstructorArgTargetBuilder<T>(this.binding.getType()));
        return this;
    }

    public BindingBuilder<T> constructorArg(Class<?> type, Object value) {
        binding.constructorArg(new ConstructorArg(type, value));
        return this;
    }

    public BindingBuilder<T> toId(String id) {
        this.binding.setId(id);
        return this;
    }

    public BindingBuilder<T> property(String name, Object value) {
        this.binding.property(name, value);
        return this;
    }

    public Binding<T> build() {
        return this.binding;
    }

    public BindingBuilder<T> depends(String propertyName, String beanName) {
        this.binding.depends(propertyName, beanName);
        return this;
    }

    public void to(Class<T> implementationOfAInterfaceClass) {
        this.binding.impClass(implementationOfAInterfaceClass);
    }
}
