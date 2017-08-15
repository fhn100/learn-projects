package org.learn.java.concurrent.design_pattern.chapter18;

public final class ActiveObjectFactory {

    private ActiveObjectFactory() {
    }

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(queue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(schedulerThread,servant);
        schedulerThread.start();
        return proxy;
    }
}