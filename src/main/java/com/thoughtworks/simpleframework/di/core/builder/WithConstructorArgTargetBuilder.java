package com.thoughtworks.simpleframework.di.core.builder;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.thoughtworks.simpleframework.di.core.ConstructorArg;
import com.thoughtworks.simpleframework.di.core.Injector;
import com.thoughtworks.simpleframework.di.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WithConstructorArgTargetBuilder<T> implements TargetBuilder<T> {

    private List<ConstructorArg> constructorArgs = new ArrayList<ConstructorArg>();
    private final Class<T> type;


    public WithConstructorArgTargetBuilder(Class<T> type) {
        this.type = type;
    }

    @Override
    public T build(Injector container) {
        T target;
        try {
            Constructor<T> constructor = (Constructor) this.type.getConstructors()[0];

            Object [] argValues = constructorArgsValue(constructor, container);

            target = constructor.newInstance(argValues);
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
        return target;
    }

    public void constructorArg(ConstructorArg arg) {
        this.constructorArgs.add(arg);
    }

    private Object[] constructorArgsValue(Constructor constructor, final Injector container) {
        Class<?>[] argTypes = constructor.getParameterTypes();
        return Collections2.transform(Arrays.asList(argTypes), new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> arg) {
                return container.get(arg);
            }
        }).toArray(new Object[0]);
    }
}
