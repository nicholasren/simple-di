package com.thoughtworks.di;

import com.example.User;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class InjectorTest {

    @Test
    public void should_create_bean_through_constructor_injecting() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                create("user").bind(com.example.User.class);
            }
        });
        assertThat(injector.get("user"), notNullValue());
    }

    @Test
    public void should_create_multi_bean_through_constructor_injecting() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                create("user").bind(com.example.User.class);
                create("phone").bind(com.example.Phone.class);
            }
        });

        assertTrue(injector.get("user") instanceof com.example.User);
        assertTrue(injector.get("phone") instanceof com.example.Phone);
    }

    @Test
    public void should_create_bean_with_given_constructor_arg() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                create("user").bind(User.class).constructorArg(String.class, "John");
            }
        });

        assertThat(((User) injector.get("user")).getName(), is("John"));
    }
}
