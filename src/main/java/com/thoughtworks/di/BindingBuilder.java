package com.thoughtworks.di;

public class BindingBuilder<T> {
    private Binding<T> binding;

    public BindingBuilder(Class<T> type) {
        this.binding = new Binding<T>(type);
    }

    public BindingBuilder<T> withConstructorArg() {
        this.binding.withConstructorArg();
        return this;
    }

    public BindingBuilder<T> constructorArg(Class<?> type, Object value) {
        binding.addConstructorArg(new ConstructorArg(type, value));
        return this;
    }

    public BindingBuilder<T> to(String name) {
        this.binding.setName(name);
        return this;
    }

    public BindingBuilder<T> property(String name, Object value) {
        this.binding.addProperties(name, value);
        return this;
    }

    public Binding<T> build() {
        return this.binding;
    }

    public BindingBuilder<T> depends(String propertyName, String beanName) {
        this.binding.depends(propertyName, beanName);
        return this;
    }
}
