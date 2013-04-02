package com.thoughtworks.di;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.thoughtworks.di.exception.BeanCreationException;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

class WithConstructorArgTargetBuilder<T> implements TargetBuilder<T> {

    private List<ConstructorArg> constructorArgs = new ArrayList<ConstructorArg>();
    private final Class<T> type;


    public WithConstructorArgTargetBuilder(Class<T> type) {
        this.type = type;
    }

    public void addConstructorArg(ConstructorArg arg) {
        constructorArgs.add(arg);
    }

    @Override
    public T build() {
        T target;

        Class<?>[] argsType = getConstructorArgsType();

        Object[] argsValue = getConstructorArgsValue();

        try {
            Constructor<T> constructor = this.type.getConstructor(argsType);
            target = constructor.newInstance(argsValue);
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
        return target;
    }

    private Object[] getConstructorArgsValue() {
        return Collections2.transform(this.constructorArgs, new Function<ConstructorArg, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable ConstructorArg arg) {
                return arg.getValue();
            }
        }).toArray(new Object[0]);
    }

    private Class<?>[] getConstructorArgsType() {
        return Collections2.transform(this.constructorArgs, new Function<ConstructorArg, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable ConstructorArg input) {
                return input.getType();
            }
        }).toArray(new Class<?>[0]);
    }

}
