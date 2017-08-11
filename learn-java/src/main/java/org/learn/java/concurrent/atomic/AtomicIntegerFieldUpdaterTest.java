package org.learn.java.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {
    class Data {
        public int getValue0 = 0;
        public volatile int value1 = 1;
        volatile int value2 = 2;
        protected volatile int value3 = 3;
        private volatile int value4 = 4;


    }

    AtomicIntegerFieldUpdater<Data> getUpdater(String fieldName) {
        return AtomicIntegerFieldUpdater.newUpdater(Data.class, fieldName);
    }


}
