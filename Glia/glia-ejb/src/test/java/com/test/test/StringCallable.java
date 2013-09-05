package com.test.test;

import java.util.concurrent.Callable;

/**
 */
public class StringCallable implements Callable<String> {

    private String someValue;

    StringCallable(String name) {
        this.someValue = name;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(5000);
        return this.someValue;
    }
}

