package com.thoughtworks.di.exception;

public class BeanCreationException extends RuntimeException {
    public BeanCreationException(Throwable e) {
        super(e);
    }

    public BeanCreationException(String message) {
        super(message);
    }
}
