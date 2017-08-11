package org.learn.java.proxy.static_proxy;

import org.learn.java.proxy.IUserService;

public class UserStaticProxy implements IUserService {

    private IUserService userService;

    public UserStaticProxy(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Integer getAge(Long id) {
        System.out.println("call method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " by static proxy start");
        Integer age = userService.getAge(id);
        System.out.println("call method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " by static proxy end");
        return age;
    }

    @Override
    public String getName(Long id) {
        System.out.println("call method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " by static proxy start");
        String name = userService.getName(id);
        System.out.println("call method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " by static proxy end");
        return name;
    }
}
