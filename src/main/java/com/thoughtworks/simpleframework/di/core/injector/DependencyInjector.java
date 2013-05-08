package com.thoughtworks.simpleframework.di.core.injector;

import com.thoughtworks.simpleframework.di.core.Injector;

public abstract class DependencyInjector<T> {

    protected final Class<T> type;

    public DependencyInjector(Class<T> type) {
        this.type = type;
    }

    abstract void inject(T target, Injector injector);
}
