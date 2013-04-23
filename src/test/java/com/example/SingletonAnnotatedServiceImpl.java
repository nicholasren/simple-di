package com.example;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.di.core.Lifecycle;

@Component(lifecycle = Lifecycle.Singleton)
public class SingletonAnnotatedServiceImpl implements Service{

    @Override
    public String service() {
        return null;
    }
}
