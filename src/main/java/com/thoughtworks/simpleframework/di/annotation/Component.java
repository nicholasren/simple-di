package com.thoughtworks.simpleframework.di.annotation;

import com.thoughtworks.simpleframework.di.core.Lifecycle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    Lifecycle lifecycle() default Lifecycle.Singleton;
}
