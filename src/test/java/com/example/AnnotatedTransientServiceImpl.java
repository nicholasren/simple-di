package com.example;

import com.thoughtworks.simpleframework.di.annotation.Component;
import com.thoughtworks.simpleframework.di.core.Lifecycle;

@Component(lifecycle = Lifecycle.Transient)
public class AnnotatedTransientServiceImpl implements Service{
    @Override
    public String service() {
        return null;
    }
}
