package com.thoughtworks.di;

import com.example.*;
import com.thoughtworks.di.core.Configuration;
import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.exception.BeanCreationException;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class InjectorTest {

    @Test
    public void should_create_bean_through_default_constructor() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithDefaultConstructor.class).toId("user");
            }
        });
        assertThat(injector.get("user", ClassWithDefaultConstructor.class), notNullValue());
    }

    @Test(expected = BeanCreationException.class)
    public void should_throw_error_when_creating_a_instance_of_interface() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(AInterface.class);
            }
        });
        injector.get(AInterface.class);
    }

    @Test
    public void should_create_multi_bean_through_default_constructor() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithDefaultConstructor.class).toId("user");
                bind(com.example.AnotherClassWithDefaultConstructor.class).toId("phone");
            }
        });

        assertThat(injector.get(ClassWithDefaultConstructor.class), instanceOf(ClassWithDefaultConstructor.class));
        assertThat(injector.get(AnotherClassWithDefaultConstructor.class), instanceOf(AnotherClassWithDefaultConstructor.class));
    }

    @Test
    public void should_return_bean_though_interface() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(AClass.class);
            }
        });
        assertThat(injector.get(AInterface.class), instanceOf(AInterface.class));
    }

    @Test
    public void should_return_bean_though_type() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(AClass.class);
            }
        });

        assertThat(injector.get(AClass.class), instanceOf(AClass.class));
    }

    @Test
    public void should_create_bean_though_setter_injecting() {
        Injector injector = Injector.create(new Configuration() {
            @Override
            protected void configure() {
                bind(ClassWithSetter.class).toId("user").property("name", "John");
            }
        });

        assertThat(injector.get("user", ClassWithSetter.class).getName(), is("John"));
    }


    @Test
    public void should_create_bean_though_constructor_arg_injecting() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithArgedConstructor.class).toId("user").withConstructorArg().constructorArg(String.class, "John");
            }
        });

        assertThat((injector.get("user", ClassWithArgedConstructor.class)).getArg(), is("John"));
    }

    @Test
    public void should_resolve_bean_dependency_by_reference() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithProperty.class).toId("phone").property("property", "Samsung");
                bind(ClassWithReferenceProperty.class).toId("user").depends("refProperty", "phone");
            }
        });

        assertThat(injector.get("user", ClassWithReferenceProperty.class).getReferenceProperty().getProperty(), is("Samsung"));
    }

    @Test
    public void should_resolve_bean_dependency_by_type() {
        Injector injector = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithProperty.class).property("property", "1.0");
                bind(ClassWithAnnotatedField.class);
            }
        });

        assertThat(injector.get(ClassWithAnnotatedField.class).getField().getProperty(), is("1.0"));
    }

    @Test
    public void should_find_bean_from_parent_container() {
        Injector parent = Injector.create(new Configuration() {
            protected void configure() {
                bind(AnotherClassWithDefaultConstructor.class);
            }
        });

        Injector child = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithDefaultConstructor.class);
            }
        }, parent);

        assertThat(child.get(AnotherClassWithDefaultConstructor.class), notNullValue());
    }

    @Test
    public void bean_from_child_container_has_higher_priority() {
        Injector parent = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithProperty.class).property("property", "Apple");
            }
        });

        Injector child = Injector.create(new Configuration() {
            protected void configure() {
                bind(ClassWithProperty.class).property("property", "Samsung");
            }
        }, parent);

        assertThat(child.get(ClassWithProperty.class).getProperty(), is("Samsung"));
    }
}