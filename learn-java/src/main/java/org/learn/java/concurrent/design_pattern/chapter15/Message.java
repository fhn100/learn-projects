package org.learn.java.concurrent.design_pattern.chapter15;

public class Message {
    private final String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}