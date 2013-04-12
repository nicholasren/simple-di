package com.thoughtworks.di.core;


import com.thoughtworks.di.core.builder.WithConstructorArgTargetBuilder;

public class BindingBuilder<T> {
    private Binding<T> binding;

    public BindingBuilder(Class<T> type) {
        if (type.isInterface()) {
            this.binding = new Binding<T>();
            this.binding.setInterfaceClass(type);
        } else {
            this.binding = new Binding<T>(type);
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

    public BindingBuilder<T> to(Class<T> type) {
        this.binding.setType(type);
        this.binding.makeInjectors(type);
        return this;
    }

    public BindingBuilder<T> in(Lifecycle lifecycle){
        return null;
    }

    public void to(T instance) {
    }
}
