package org.learn.java.concurrent.design_pattern.chapter1;

public class SingletonObject3 {
    private static SingletonObject3 instance;

    private SingletonObject3() {
        //empty
    }

    public synchronized static SingletonObject3 getInstance() {

        if (null == instance)
            instance = new SingletonObject3();

        return SingletonObject3.instance;
    }
}