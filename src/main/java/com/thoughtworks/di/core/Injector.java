package com.thoughtworks.di.core;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.thoughtworks.di.exception.BeanCreationException;

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
            public boolean apply(Binding binding) {
                return binding.getId().equals(id);
            }
        });

        return foundBindings.isEmpty() ? parent.get(id, type) : (T) firstOf(foundBindings);

    }

    public <T> T get(final Class<T> type) {

        Collection<Binding> bound = typeBound(type);

        if (!bound.isEmpty()) {
            return (T) firstOf(bound);
        }

        Collection<Binding> typeMatched = typeMatchedBindings(type);

        if (!typeMatched.isEmpty()) {
            return (T) firstOf(typeMatched);
        }

        return parent.get(type);
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


    private <T> Collection<Binding> typeMatchedBindings(final Class<T> type) {
        Predicate<Binding> typeOf = new Predicate<Binding>() {
            @Override
            public boolean apply(Binding binding) {
                if (binding.getType() == null) {
                    throw new BeanCreationException("Can not create bean without supplying implementation class for " + binding.getInterfaceClass());
                }
                return typeOf(binding.getType(), type);
            }

            private boolean typeOf(Class<T> actual, Class<T> clazz) {
                return Arrays.asList(actual.getInterfaces()).contains(clazz) || actual.equals(clazz);
            }
        };

        return Collections2.filter(bindings, typeOf);
    }

    private <T> Collection<Binding> typeBound(final Class<T> type) {
        Predicate<Binding> boundTo = new Predicate<Binding>() {
            @Override
            public boolean apply(Binding binding) {
                if (binding.getType() == null) {
                    throw new BeanCreationException("Can not create bean without supplying implementation class for " + binding.getInterfaceClass());
                }
                return binding.getInterfaceClass() == type;
            }
        };


        return Collections2.filter(bindings, boundTo);
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
