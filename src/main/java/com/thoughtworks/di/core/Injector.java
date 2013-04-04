package com.thoughtworks.di.core;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.Arrays;
import java.util.Collection;


public class Injector {

    private final Collection<Binding> bindings;

    public Injector(Collection<Binding> bindings) {
        this.bindings = bindings;
        injectingContainer(this.bindings);
    }


    public static Injector create(Configuration configuration) {
        configuration.configure();
        Collection<Binding> bindings = buildBindings(configuration);
        return new Injector(bindings);
    }


    public <T> T get(final String name, Class<T> type) {
        Collection<Binding> foundBindings = Collections2.filter(bindings, new Predicate<Binding>() {
            @Override
            public boolean apply(@javax.annotation.Nullable Binding binding) {
                return binding.getName().equals(name);
            }
        });

        return firstOf(foundBindings);
    }


    public <T> T get(final Class<T> type) {

        Collection<Binding> foundBindings = Collections2.filter(bindings, new Predicate<Binding>() {
            @Override
            public boolean apply(Binding binding) {
                return typeOf(binding.getType(), type);
            }

            private boolean typeOf(Class<T> actual, Class<T> clazz) {
                return Arrays.asList(actual.getInterfaces()).contains(clazz) || actual.equals(clazz);
            }
        });
        return firstOf(foundBindings);
    }

    private static Collection<Binding> buildBindings(Configuration configuration) {
        return Collections2.transform(configuration.getBuilders(), new Function<BindingBuilder, Binding>() {
            @Override
            public Binding apply(BindingBuilder builder) {
                return builder.build();
            }
        });
    }

    private void injectingContainer(Collection<Binding> bindings) {
        for (Binding binding : bindings) {
            binding.setInjector(this);
        }
    }

    private <T> T firstOf(Collection<Binding> bindings) {
        return bindings.isEmpty() ? null : (T) bindings.toArray(new Binding[0])[0].getTarget();
    }


}
