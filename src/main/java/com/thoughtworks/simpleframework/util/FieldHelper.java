package com.thoughtworks.simpleframework.util;

import java.lang.reflect.Method;

import static com.thoughtworks.simpleframework.util.Lang.makeThrow;
import static com.thoughtworks.simpleframework.util.Lang.stackTrace;

public class FieldHelper {

    public static Long getId(Object obj) {
        Long id;
        try {
            Method method = obj.getClass().getMethod("getId");
            id = (Long) method.invoke(obj);
        } catch (Exception e) {
            throw makeThrow("Exception encountered when get id of obj, message: %s, stacktrace: %s", e.getMessage(), stackTrace(e));
        }
        return id;
    }

}
