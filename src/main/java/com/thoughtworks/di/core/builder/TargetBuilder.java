package com.thoughtworks.di.core.builder;

import com.thoughtworks.di.core.ConstructorArg;
import com.thoughtworks.di.core.Injector;

public interface TargetBuilder<T> {
    T build(Injector injector);

    void constructorArg(ConstructorArg arg);
}
