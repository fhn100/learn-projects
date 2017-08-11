package org.learn.java.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkDynamicProxy implements InvocationHandler{

    private Object target;

    public JdkDynamicProxy() {
    }

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("call method " + method.getName() + " by jdk dynamic proxy start");
        Object result = method.invoke(target, args);
        System.out.println("call method " + method.getName() + " by jdk dynamic proxy start");
        return result;
    }
}
