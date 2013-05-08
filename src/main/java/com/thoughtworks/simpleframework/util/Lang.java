package com.thoughtworks.simpleframework.util;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.simpleframework.di.exception.LoadFailedException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;


public class Lang {

    public static Collection<Class> getClassInfos(String packageName) {
        ClassPath classPath;
        try {
            classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        } catch (IOException e) {
            throw new LoadFailedException(e);
        }

        return Collections2.transform(classPath.getTopLevelClassesRecursive(packageName), new Function<ClassPath.ClassInfo, Class>() {
            @Override
            public Class apply(com.google.common.reflect.ClassPath.ClassInfo input) {
                return input.load();
            }
        });
    }

    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }

    public static Method methodFor(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            throw makeThrow("can not find method %s for class %s", clazz.getCanonicalName(), name);
        }
    }

    public static String stackTrace(Throwable e) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            buffer.append(element.toString()).append("\n");
        }
        return buffer.toString();
    }

    public static Object instanceFor(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw makeThrow("Create instance failed for class %s, error: %s", clazz.getCanonicalName(), stackTrace(e));
        }
        return instance;
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.equals(Integer.class) ||
                type.equals(Long.class) ||
                type.equals(Float.class) ||
                type.equals(Double.class) ||
                type.equals(String.class) ||
                type.equals(Boolean.class);
    }

    public static Collection<Field> getAnnotatedField(Class<?> clazz, final Class<? extends Annotation> annotationClazz) {

        Collection<Field> allFields = Arrays.asList(clazz.getDeclaredFields());

        return Collections2.filter(allFields, new Predicate<Field>() {
            @Override
            public boolean apply(Field field) {
                return field.isAnnotationPresent(annotationClazz);
            }
        });
    }

}
