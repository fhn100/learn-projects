package org.learn.java.concurrent.design_pattern.chapter18;


/**
 * 接受异步消息的主动对象
 */
public interface ActiveObject {

    Result makeString(int count,char fillChar);

    void displayString(String text);
}
