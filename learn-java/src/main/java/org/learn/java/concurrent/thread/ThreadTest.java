package org.learn.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

public class ThreadTest {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("我是实现了Runnable的线程");
			}
		}) {
			// 此时重写了Thread类的run方法，所以运行的是这里的run
			@Override
			public void run() {
				System.out.println("我是继承了Thread类的线程");
			}
		};
		thread.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}

	}
}
