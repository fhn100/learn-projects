package org.learn.java.concurrent.design_pattern.chapter4;

public interface LifeCycleListener {
    
    void onEvent(ObservableRunnable.RunnableEvent event);
}
