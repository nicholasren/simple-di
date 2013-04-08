package com.thoughtworks.di.core;

public class Binding<T> {
    private String id;
    private Class<T> type;
    private TargetBuilder targetBuilder;
    private PropertyInjector propertyInjector;
    private DependentReferenceInjector dependentReferenceInjector;
    private AnnotatedFieldInjector annotatedFieldInjector;
    private AnnotatedSetterInjector<T> annotatedSetterInjector;
    private Class<T> interfaceClass;

    public Binding(Class<T> type) {
        this.type = type;
    }


    public Binding() {

    }

    public T getTarget(Injector injector) {

        T target = (T) this.targetBuilder.build();

        if (null != propertyInjector) {
            this.propertyInjector.inject(target, injector);
        }

        if (null != dependentReferenceInjector) {
            this.dependentReferenceInjector.inject(target, injector);
        }

        this.annotatedFieldInjector.inject(target, injector);
        this.annotatedSetterInjector.inject(target, injector);

        return target;
    }

    public void constructorArg(ConstructorArg arg) {
        ((WithConstructorArgTargetBuilder) this.targetBuilder).constructorArg(arg);
    }

    public void property(String name, Object value) {
        if (null == this.propertyInjector) {
            this.propertyInjector = new PropertyInjector(type);
        }
        this.propertyInjector.property(name, value);
    }


    public void depends(String propertyName, String beanName) {
        if (null == this.dependentReferenceInjector) {
            this.dependentReferenceInjector = new DependentReferenceInjector(type);
        }
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

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void makeInjectors() {
        this.targetBuilder = new DefaultTargetBuilder(type);
        this.annotatedFieldInjector = new AnnotatedFieldInjector(type);
        this.annotatedSetterInjector = new AnnotatedSetterInjector<T>(type);
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }
}
