package com.thoughtworks.simpleframework.di.core.builder;

import com.thoughtworks.simpleframework.di.core.ConstructorArg;
import com.thoughtworks.simpleframework.di.core.Injector;

public interface TargetBuilder<T> {
    T build(Injector injector);

    void constructorArg(ConstructorArg arg);
}
