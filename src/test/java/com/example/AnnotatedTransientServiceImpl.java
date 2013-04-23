package com.example;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.di.core.Lifecycle;

@Component(lifecycle = Lifecycle.Transient)
public class AnnotatedTransientServiceImpl implements Service{
    @Override
    public String service() {
        return null;
    }
}
