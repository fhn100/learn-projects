package org.learn.java.class_loader.chapter5;

public class RuntimePackage {
    //RuntimePackage
    //com.wangwenjun.concurrent.classloader.chapter5
    //Boot.Ext.App.com.wangwenjun.concurrent.classloader.chapter5

    //Boot.Ext.App.com.wangwenjun.concurrent.classloader.chapter5.SimpleClassLoaderTest
    //Boot.Ext.App.SimpleClassLoader.com.wangwenjun.concurrent.classloader.chapter5.SimpleClassLoaderTest

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
		Class<?> aClass = simpleClassLoader.loadClass("org.learn.java.class_loader.chapter5.SimpleObject");
		System.out.println(aClass);
        SimpleObject simpleObject = (SimpleObject) aClass.newInstance();
		System.out.println(simpleObject);
    }
}
