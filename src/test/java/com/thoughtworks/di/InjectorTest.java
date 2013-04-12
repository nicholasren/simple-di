package com.thoughtworks.di;

import com.example.*;
import com.thoughtworks.di.core.Configuration;
import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.core.Lifecycle;
import com.thoughtworks.di.exception.BeanCreationException;
import com.thoughtworks.di.exception.CyclicDependencyException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class InjectorTest {

    private Injector injector;

    @Test
    public void should_be_able_to_create_instance_with_zero_constructor() {
        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(Service.class).to(ServiceImplementation.class);
            }
        });

        Service service = injector.get(Service.class);
        assertThat(service.service(), is(ServiceImplementation.class.getCanonicalName()));
    }

    @Test
    public void should_be_able_to_inject_service_to_constructor() {

        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceConsumer.class).to(ServiceConsumerImplementation.class);
                bind(Service.class).to(ServiceImplementation.class);
            }
        });

        ServiceConsumer consumer = injector.get(ServiceConsumer.class);
        assertThat(consumer.service(), is(ServiceImplementation.class.getCanonicalName()));
    }

    @Test(expected = CyclicDependencyException.class)
    public void should_throw_exception_if_cyclic_dependency() {

        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceConsumer.class).to(ServiceConsumerImplementation.class);
                bind(Service.class).to(BadService.class);
            }
        });


        injector.get(ServiceConsumer.class);
    }

    @Test
    public void should_inject_instance_to_constructor() {

        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceConsumer.class).to(ServiceConsumerImplementation.class);
                bind(Service.class).to(PrivateService.getInstance());
            }
        });

        ServiceConsumer consumer = injector.get(ServiceConsumer.class);
        assertThat(consumer.service(), is(PrivateService.class.getCanonicalName()));
    }

    @Test
    public void should_be_able_to_declare_service_lifecycle() {

        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(Service.class).to(ServiceImplementation.class).in(Lifecycle.Singleton);
                bind(ServiceConsumer.class).to(TransientServiceConsumer.class).in(Lifecycle.Transient);
            }
        });

        TransientServiceConsumer first = (TransientServiceConsumer) injector.get(ServiceConsumer.class);
        TransientServiceConsumer second = (TransientServiceConsumer) injector.get(ServiceConsumer.class);

        assertThat(first, not(sameInstance(second)));
        assertThat(first.getService(), sameInstance(second.getService()));
    }

    @Test
    public void should_be_able_to_inject_service_via_setter() {

        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(Service.class).to(ServiceImplementation.class);
                bind(ServiceConsumer.class).to(SetterConsumer.class);
            }
        });

        ServiceConsumer consumer = injector.get(ServiceConsumer.class);
        assertThat(consumer.service(), is(ServiceImplementation.class.getCanonicalName()));
    }

    @Test
    public void should_find_service_from_parent_container() {
        Injector grandfather = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(Service.class).to(ServiceImplementation.class);
            }
        });

        Injector father = Injector.create(new Configuration() {
            @Override
            protected void configure() {
            }
        }, grandfather);

        Injector son = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(Service.class).to(ServiceImplementation.class);
                bind(ServiceConsumer.class).to(SetterConsumer.class);
            }
        }, father);

        ServiceConsumer consumer = son.get(ServiceConsumer.class);
        assertThat(consumer.service(), is(ServiceImplementation.class.getCanonicalName()));
    }

    @Test(expected = BeanCreationException.class)
    public void should_throw_component_not_found_exception() {
        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceConsumer.class).to(SetterConsumer.class);
            }
        });
        injector.get(Service.class);
    }
}
