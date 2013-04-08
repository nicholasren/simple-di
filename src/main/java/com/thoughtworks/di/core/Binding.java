package com.thoughtworks.di.core;

public class Binding<T> {
    private String id;
    private Class<T> type;
    private TargetBuilder targetBuilder;
    private PropertyInjector propertyInjector;
    private DependentReferenceInjector dependentReferenceInjector;
    private AnnotatedFieldInjector annotatedFieldInjector;
    private AnnotatedSetterInjector<T> annotatedSetterInjector;


    public Binding(Class<T> type) {
        this.type = type;
        this.targetBuilder = new DefaultTargetBuilder(type);
        this.propertyInjector = new PropertyInjector(type);
        this.dependentReferenceInjector = new DependentReferenceInjector(type);
        this.annotatedFieldInjector = new AnnotatedFieldInjector(type);
        this.annotatedSetterInjector = new AnnotatedSetterInjector<T>(type);
    }

    public T getTarget(Injector injector) {

        T target = (T) this.targetBuilder.build();

        this.propertyInjector.inject(target, injector);

        this.dependentReferenceInjector.inject(target, injector);

        this.annotatedFieldInjector.inject(target, injector);

        this.annotatedSetterInjector.inject(target, injector);

        return target;
    }

    public void constructorArg(ConstructorArg arg) {
        ((WithConstructorArgTargetBuilder) this.targetBuilder).constructorArg(arg);
    }

    public void property(String name, Object value) {
        this.propertyInjector.property(name, value);
    }


    public void depends(String propertyName, String beanName) {
        this.dependentReferenceInjector.depends(propertyName, beanName);
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

    public void setTargetBuilder(WithConstructorArgTargetBuilder<T> targetBuilder) {
        this.targetBuilder = targetBuilder;
    }
}
