package com.thoughtworks.di;

public class BindingBuilder<T> {
    private Binding<T> binding;

    public BindingBuilder(Class<T> type) {
        this.binding = new Binding<T>();
        this.binding.setType(type);
    }

    public BindingBuilder<T> constructorArg(Class<?> type, Object value) {
        this.binding.addConstructorArgTypes(type);
        this.binding.addConstructorArgValues(value);
        return this;
    }

    public BindingBuilder<T> to(String name) {
        this.binding.setName(name);
        return this;
    }

    public BindingBuilder property(String name, Object value) {
        this.binding.addProperties(name, value);
        return this;
    }

    public Binding<T> build() {
        return this.binding;
    }
}
