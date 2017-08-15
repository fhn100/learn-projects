package org.learn.java.concurrent.design_pattern.chapter1;

public class SingletonObject2 {

    private static SingletonObject2 instance;

    private SingletonObject2() {
        //empty
    }

    public static SingletonObject2 getInstance() {
        if (null == instance)
            instance = new SingletonObject2();

        return SingletonObject2.instance;
    }
}
