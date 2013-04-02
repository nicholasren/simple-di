package com.thoughtworks.di;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.Collection;


public class Injector {

    private final Collection<Binding> bindings;

    public Injector(Collection<Binding> bindings) {
        this.bindings = bindings;
        for (Binding binding : this.bindings) {
            binding.setInjector(this);
        }
    }

    public static Injector create(Configuration configuration) {
        configuration.configure();
        Collection<Binding> bindings = buildBindings(configuration);
        return new Injector(bindings);
    }


    public Object get(final String name) {
        Binding binding = (Binding) Collections2.filter(bindings, new Predicate<Binding>() {
            @Override
            public boolean apply(@javax.annotation.Nullable Binding binding) {
                return binding.getName().equals(name);
            }
        }).toArray()[0];

        return binding == null ? null : binding.getTarget();
    }

    private static Collection<Binding> buildBindings(Configuration configuration) {
        return Collections2.transform(configuration.getBuilders(), new Function<BindingBuilder, Binding>() {
            @Override
            public Binding apply(BindingBuilder builder) {
                return builder.build();
            }
        });
    }
}
