package org.learn.java.concurrent.design_pattern.chapter9;

public class Request {
    final private String value;

    public Request(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
