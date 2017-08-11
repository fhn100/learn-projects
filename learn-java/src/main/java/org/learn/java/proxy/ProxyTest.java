package org.learn.java.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.learn.java.proxy.dynamic_proxy.CglibDynamicProxy;
import org.learn.java.proxy.dynamic_proxy.JdkDynamicProxy;
import org.learn.java.proxy.static_proxy.UserStaticProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        testStaticProxy();
        testJdkDynamicProxy();
        testCglibDynamicProxy();
    }

    private static void testStaticProxy() {
        IUserService userService = new UserService();
        UserStaticProxy proxy = new UserStaticProxy(userService);
        System.out.println(proxy.getAge(0L));
        System.out.println(proxy.getName(0L));
    }

    private static void testJdkDynamicProxy() {
        IUserService userService = new UserService();
        InvocationHandler invocationHandler = new JdkDynamicProxy(userService);
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                userService.getClass().getInterfaces(), invocationHandler);
        System.out.println(proxy.getAge(0L));
        System.out.println(proxy.getName(0L));
    }

    private static void testCglibDynamicProxy() {
        CglibDynamicProxy proxy = new CglibDynamicProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(proxy);

        IUserService userService = (IUserService) enhancer.create();
        System.out.println(userService.getAge(0L));
        System.out.println(userService.getName(0L));
    }
}
