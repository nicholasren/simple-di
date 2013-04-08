package com.thoughtworks.di.core;

public abstract class DependencyInjector<T> {

    protected final Class<T> type;

    public DependencyInjector(Class<T> type) {
        this.type = type;
    }

    abstract void inject(T target, Injector injector);
}
