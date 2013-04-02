package com.thoughtworks.di;

import com.example.Phone;
import com.example.User;
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
        assertThat(injector.get("user"), notNullValue());
    }

    @Test
    public void should_create_multi_bean_through_default_constructor() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(com.example.User.class).to("user");
                bind(com.example.Phone.class).to("phone");
            }
        });

        assertTrue(injector.get("user") instanceof com.example.User);
        assertTrue(injector.get("phone") instanceof com.example.Phone);
    }

    @Test
    public void should_create_bean_though_setter_injecting() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            void configure() {
                bind(User.class).to("user").property("name", "John");
            }
        });

        assertThat(((User) injector.get("user")).getName(), is("John"));
    }


    @Test
    public void should_create_bean_though_constructor_arg_injecting() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(User.class).to("user").withConstructorArg().constructorArg(String.class, "John");
            }
        });

        assertThat(((User) injector.get("user")).getName(), is("John"));
    }

    @Test
    public void should_resolve_bean_dependency() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(Phone.class).to("phone1").property("type", "Samsung");
                bind(User.class).to("user").depends("phone", "phone1");
            }
        });

        assertThat(((User) injector.get("user")).getPhone().getType(), is("Samsung"));
    }
}
