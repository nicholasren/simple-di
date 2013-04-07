package com.thoughtworks.di.core;

public class Binding<T> {
    private String id;
    private Class<T> type;
    private TargetBuilder targetBuilder;
    private PropertyInjector propertyInjector;
    private DependencyInjector dependencyInjector;
    private AnnotatedFieldInjector annotatedFieldInjector;


    public Binding(Class<T> type) {
        this.type = type;
        this.targetBuilder = new DefaultTargetBuilder(type);
        this.propertyInjector = new PropertyInjector(type);
        this.dependencyInjector = new DependencyInjector(type);
        this.annotatedFieldInjector = new AnnotatedFieldInjector(type);
    }

    public T getTarget(Injector injector) {

        T target = (T) this.targetBuilder.build();

        this.propertyInjector.inject(target);

        this.dependencyInjector.inject(target, injector);

        this.annotatedFieldInjector.inject(target, injector);

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Class<T> getType() {
        return type;
    }
}
