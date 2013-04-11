package com.thoughtworks.di.core.builder;

import com.thoughtworks.di.core.ConstructorArg;

public interface TargetBuilder<T> {
    T build();

    void constructorArg(ConstructorArg arg);
}
