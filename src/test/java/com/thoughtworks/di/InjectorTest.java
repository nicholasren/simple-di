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
                bind(com.example.User.class).to("user");
            }
        });
        assertThat(injector.get("user"), notNullValue());
    }

    @Test
    public void should_create_multi_bean_through_constructor_injecting() {
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
    public void should_create_bean_with_given_constructor_arg() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(User.class).to("user").constructorArg(String.class, "John");
            }
        });

        assertThat(((User) injector.get("user")).getName(), is("John"));
    }
}
