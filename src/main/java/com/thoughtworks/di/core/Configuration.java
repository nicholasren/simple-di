package com.thoughtworks.di.core;


import java.util.LinkedList;
import java.util.List;

public abstract class Configuration {

    public List<BindingBuilder> builders = new LinkedList<BindingBuilder>();



    public <T> BindingBuilder create(Class<T> clazz) {
        BindingBuilder builder = new BindingBuilder(clazz);
        builders.add(builder);
        return builder;
    }

    protected abstract void configure();

    public List<BindingBuilder> getBuilders() {
        return builders;
    }
}
