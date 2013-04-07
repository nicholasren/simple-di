package com.thoughtworks.di.core;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.Arrays;
import java.util.Collection;


public class Injector {

    private static Injector NULL = new NullInjector();
    private Collection<Binding> bindings;
    private Injector parent = NULL;

    public static Injector create(Configuration configuration) {
        return create(configuration, null);
    }

    public static Injector create(Configuration configuration, Injector parent) {
        configuration.configure();
        return new Injector(buildBindings(configuration), parent);
    }

    public <T> T get(final String id, Class<T> type) {
        Collection<Binding> foundBindings = Collections2.filter(bindings, new Predicate<Binding>() {
            @Override
            public boolean apply(@javax.annotation.Nullable Binding binding) {
                return binding.getId().equals(id);
            }
        });

        return foundBindings.isEmpty() ? parent.get(id, type) : (T) firstOf(foundBindings);

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

        return foundBindings.isEmpty() ? parent.get(type) : (T) firstOf(foundBindings);
    }

    private static Collection<Binding> buildBindings(Configuration configuration) {
        return Collections2.transform(configuration.getBuilders(), new Function<BindingBuilder, Binding>() {
            @Override
            public Binding apply(BindingBuilder builder) {
                return builder.build();
            }
        });
    }



    private <T> T firstOf(Collection<Binding> bindings) {
        return bindings.isEmpty() ? null : (T) bindings.toArray(new Binding[0])[0].getTarget(this);
    }

    private Injector(Collection<Binding> bindings, Injector parent) {
        this.bindings = bindings;
        this.parent = parent;
    }

    private Injector() {
    }

    private static class NullInjector extends Injector {
        public <T> T get(final String id, Class<T> type) {
            return null;
        }

        public <T> T get(Class<T> type) {
            return null;
        }
    }

}
