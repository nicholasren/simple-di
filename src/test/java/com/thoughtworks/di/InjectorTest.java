package com.thoughtworks.di;

import com.example.*;
import com.thoughtworks.di.core.Configuration;
import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.core.Lifecycle;
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

    @Test
    public void should_throw_component_not_found_exception() {
        injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceConsumer.class).to(SetterConsumer.class);
            }
        });
        assertThat(injector.get(Service.class), nullValue());
    }

    @Test
    public void should_load_annotated_transient_component() {
        injector = Injector.create("com.example");

        Service service1 = injector.get(AnnotatedTransientServiceImpl.class);
        Service service2 = injector.get(AnnotatedTransientServiceImpl.class);

        assertThat(service1, not(sameInstance(service2)));
    }

    @Test
    public void should_load_annotated_singleton_component() {
        injector = Injector.create("com.example");
        Service service1 = injector.get(SingletonAnnotatedServiceImpl.class);
        Service service2 = injector.get(SingletonAnnotatedServiceImpl.class);

        assertThat(service1, sameInstance(service2));
    }

    @Test
    public void should_load_annotated_component_from_parent_container(){
        injector = Injector.create("com.example");
    }


    @Test
    public void should_not_load_instance_for_null_type() {
        Injector parent = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceConsumer.class).to(SetterConsumer.class);
            }
        });


        injector = Injector.create("com.example", parent);
        assertThat(injector.get(ServiceConsumer.class), instanceOf(SetterConsumer.class));
    }
}
