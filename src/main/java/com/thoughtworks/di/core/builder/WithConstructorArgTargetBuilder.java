package com.thoughtworks.di.core.builder;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.thoughtworks.di.core.ConstructorArg;
import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class WithConstructorArgTargetBuilder<T> implements TargetBuilder<T> {

    private List<ConstructorArg> constructorArgs = new ArrayList<ConstructorArg>();
    private final Class<T> type;


    public WithConstructorArgTargetBuilder(Class<T> type) {
        this.type = type;
    }

    @Override
    public T build() {
        T target;
        try {
            Constructor<T> constructor = this.type.getConstructor(constructorArgsType());
            target = constructor.newInstance(constructorArgsValue());
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
        return target;
    }

    public void constructorArg(ConstructorArg arg) {
        this.constructorArgs.add(arg);
    }

    private Object[] constructorArgsValue() {
        return Collections2.transform(this.constructorArgs, new Function<ConstructorArg, Object>() {
            @Override
            public Object apply(ConstructorArg arg) {
                return arg.getValue();
            }
        }).toArray(new Object[0]);
    }

    private Class<?>[] constructorArgsType() {
        return Collections2.transform(this.constructorArgs, new Function<ConstructorArg, Object>() {
            @Override
            public Object apply(ConstructorArg arg) {
                return arg.getType();
            }
        }).toArray(new Class<?>[0]);
    }
}
