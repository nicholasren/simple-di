package com.thoughtworks.di;

import java.util.LinkedList;
import java.util.List;

public abstract class Configuration {

    public List<BindingBuilder> builders = new LinkedList<BindingBuilder>();

    public BindingBuilder create(String name) {
        BindingBuilder builder = new BindingBuilder(name);
        builders.add(builder);
        return builder;
    }

    abstract void configure();

    public List<BindingBuilder> getBuilders() {
        return builders;
    }
}
