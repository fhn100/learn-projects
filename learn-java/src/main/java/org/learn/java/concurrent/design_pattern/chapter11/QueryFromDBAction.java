package org.learn.java.concurrent.design_pattern.chapter11;

public class QueryFromDBAction {

    public void execute() {
        try {
            Thread.sleep(1000L);
            String name = "Alex " + Thread.currentThread().getName();
            ActionContext.getActionContext().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
