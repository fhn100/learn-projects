package org.learn.java.concurrent.design_pattern.chapter8;

public interface Future<T> {

    T get() throws InterruptedException;

}