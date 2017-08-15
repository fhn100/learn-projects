package org.learn.java.concurrent.lock.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProductQueue<T> {

    private T[] items;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int head, index, count;

	@SuppressWarnings("unchecked")
	public ProductQueue(int maxSize) {
        items = (T[]) new Object[maxSize];
    }

    public ProductQueue() {
        this(10);
    }

    public int getCapacity() {
        return items.length;
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == getCapacity()) {
                notFull.await();
            }
            items[index] = t;
            if (++index == getCapacity()) {
                index = 0;
            }
            ++count;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T ret = items[head];
            items[head] = null;//GC
            //
            if (++head == getCapacity()) {
                head = 0;
            }
            --count;
            notFull.signalAll();
            return ret;
        } finally {
            lock.unlock();
        }
    }
}
