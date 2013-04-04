package com.thoughtworks.di;

import com.example.Phone;
import com.example.Service;
import com.example.ServiceImpl;
import com.example.User;
import com.thoughtworks.di.core.Configuration;
import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.exception.BeanCreationException;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class InjectorTest {

    @Test
    public void should_create_bean_through_default_constructor() {
        Injector injector = Injector.create(new Configuration() {
             protected void configure() {
                bind(com.example.User.class).to("user");
            }
        });
        assertThat(injector.get("user", User.class), notNullValue());
    }

    @Test(expected = BeanCreationException.class)
    public void should_throw_error_when_creating_a_instance_of_interface() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(Service.class);
            }
        });
        injector.get(Service.class);
    }

    @Test
    public void should_create_multi_bean_through_default_constructor() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(com.example.User.class).to("user");
                bind(com.example.Phone.class).to("phone");
            }
        });

        assertTrue(injector.get("user", User.class) instanceof com.example.User);
        assertTrue(injector.get("phone", Phone.class) instanceof com.example.Phone);
    }

    @Test
    public void should_return_bean_though_interface() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceImpl.class).property("version", "1.0");
            }
        });

        assertTrue(injector.get(Service.class) instanceof ServiceImpl);
    }

    @Test
    public void should_return_bean_though_type() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ServiceImpl.class).property("version", "1.0");
            }
        });

        assertTrue(injector.get(ServiceImpl.class) instanceof ServiceImpl);
    }

    @Test
    public void should_create_bean_though_setter_injecting() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(User.class).to("user").property("name", "John");
            }
        });

        assertThat(injector.get("user", User.class).getName(), is("John"));
    }


    @Test
    public void should_create_bean_though_constructor_arg_injecting() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(User.class).to("user").withConstructorArg().constructorArg(String.class, "John");
            }
        });

        assertThat((injector.get("user", User.class)).getName(), is("John"));
    }

    @Test
    public void should_resolve_bean_dependency_by_reference() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(Phone.class).to("phone1").property("type", "Samsung");
                bind(User.class).to("user").depends("phone", "phone1");
            }
        });

        assertThat(injector.get("user", User.class).getPhone().getType(), is("Samsung"));
    }

    @Test
    public void should_resolve_bean_dependency_by_type() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(ServiceImpl.class).property("version", "1.0");
                bind(User.class);
            }
        });

        assertThat(injector.get(User.class).getService().getVersion(), is("1.0"));
    }
}
