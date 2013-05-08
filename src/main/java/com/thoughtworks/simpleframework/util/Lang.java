package com.thoughtworks.simpleframework.util;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.simpleframework.di.exception.LoadFailedException;

import java.io.IOException;
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
}
