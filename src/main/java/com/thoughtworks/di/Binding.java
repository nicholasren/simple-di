package com.thoughtworks.di;

public class Binding<T> {
    private String name;
    private Class<T> type;
    private TargetBuilder targetBuilder;
    private PropertyInjector propertyInjector;
    private DependencyInjector dependencyInjector;


    public Binding(Class<T> type) {
        this.type = type;
        this.targetBuilder = new DefaultTargetBuilder(type);
        this.propertyInjector = new PropertyInjector(type);
        this.dependencyInjector = new DependencyInjector(type);
    }

    public T getTarget() {

        T target = (T) this.targetBuilder.build();

        this.propertyInjector.inject(target);

        this.dependencyInjector.inject(target);

        return target;
    }

    public void constructorArg(ConstructorArg arg) {
        ((WithConstructorArgTargetBuilder) this.targetBuilder).constructorArg(arg);
    }

    public void withConstructorArg() {
        this.targetBuilder = new WithConstructorArgTargetBuilder<T>(type);
    }

    public void property(String name, Object value) {
        this.propertyInjector.property(name, value);
    }


    public void depends(String propertyName, String beanName) {
        this.dependencyInjector.depends(propertyName, beanName);
    }


    public void setInjector(Injector injector) {
        this.dependencyInjector.setInjector(injector);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
