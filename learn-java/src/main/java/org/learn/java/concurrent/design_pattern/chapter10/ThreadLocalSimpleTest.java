package org.learn.java.concurrent.design_pattern.chapter10;

public class ThreadLocalSimpleTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "Alex";
        }
    };

    //JVM start main thread
    public static void main(String[] args) throws InterruptedException {
//        threadLocal.set("Alex");
        Thread.sleep(1000);
        String value = threadLocal.get();
        System.out.println(value);
    }
}