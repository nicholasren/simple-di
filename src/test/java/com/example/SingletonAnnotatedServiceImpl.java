package com.example;

import com.thoughtworks.simpleframework.di.annotation.Component;
import com.thoughtworks.simpleframework.di.core.Lifecycle;

@Component(lifecycle = Lifecycle.Singleton)
public class SingletonAnnotatedServiceImpl implements Service{

    @Override
    public String service() {
        return null;
    }
}
