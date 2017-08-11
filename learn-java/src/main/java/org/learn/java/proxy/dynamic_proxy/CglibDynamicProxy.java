package org.learn.java.proxy.dynamic_proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibDynamicProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("call method " + method.getName() + " by cglib dynamic proxy start");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("call method " + method.getName() + " by cglib dynamic proxy end");
        return result;
    }
}
