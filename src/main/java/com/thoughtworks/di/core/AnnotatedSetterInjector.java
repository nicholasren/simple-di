package com.thoughtworks.di.core;

import com.thoughtworks.di.exception.BeanCreationException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedSetterInjector<T> extends DependencyInjector<T> {

    private List<Method> annotatedSetters = new ArrayList<Method>();

    public AnnotatedSetterInjector(Class type) {
        super(type);
        extractAnnotatedSetters();
    }

    private void extractAnnotatedSetters() {
        for (Method method : type.getMethods()) {
            if (method.isAnnotationPresent(javax.inject.Inject.class)) {
                annotatedSetters.add(method);
            }
        }
    }

    public void inject(T target, Injector injector) {

        for (Method method : annotatedSetters) {
            Object value = injector.get(getRequiredType(method));
            setValue(target, method, value);
        }

    }

    private void setValue(T target, Method method, Object value) {
        method.setAccessible(true);
        try {
            method.invoke(target, value);
        } catch (Exception e) {
            throw new BeanCreationException(e);
        }
    }

    private Class<?> getRequiredType(Method method) {
        Class<?>[] paramsTypes = method.getParameterTypes();
        if (paramsTypes.length != 1 || !method.getName().startsWith("set")) {
            throw new BeanCreationException("Can not inject value into a non-setter " + method.getName());
        }
        return paramsTypes[0];
    }
}
