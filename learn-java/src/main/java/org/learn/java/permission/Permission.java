package org.learn.java.permission;

public class Permission {
    public static void main(String[] args) {
        //权限定义
        int read = 1 << 0;
        int write = 1 << 1;
        int execute = 1 << 2;

        //赋予权限
        int permission = read | write;

        System.out.println(permission);

        System.out.println("是否有读权限:" + (permission & read));
        System.out.println("是否有写权限:" + (permission & write));
        System.out.println("是否有执行权限:" + (permission & execute));
    }
}
