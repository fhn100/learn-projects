package org.learn.java.proxy;

public class UserService implements IUserService {
    @Override
    public Integer getAge(Long id) {
        return 30;
    }

    @Override
    public String getName(Long id) {
        return "冯海年";
    }
}
