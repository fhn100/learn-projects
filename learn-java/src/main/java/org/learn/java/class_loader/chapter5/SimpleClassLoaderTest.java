package org.learn.java.class_loader.chapter5;

public class SimpleClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
        Class<?> aClass = simpleClassLoader.loadClass("java.lang.String");
//        System.out.println(aClass.getClassLoader());
    }
}
