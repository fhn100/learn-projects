package org.learn.java.concurrent.thread_state;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class ThreadState {
	public static void main(String[] args) throws Exception {
		// 创建现成数组
		Thread[] taskArr = new Thread[10];

		// 线程状态数组
		Thread.State[] threadStates = new Thread.State[10];

		// 设置线程的状态
		for (int i = 0; i < 10; i++) {
			taskArr[i] = new Thread(new Pig());

			// 分别设置状态
			if ((i % 3) == 0) {
				taskArr[i].setPriority(Thread.NORM_PRIORITY);
			} else if ((i % 3) == 1) {
				taskArr[i].setPriority(Thread.MIN_PRIORITY);
			} else if ((i % 3) == 2) {
				taskArr[i].setPriority(Thread.MAX_PRIORITY);
			}
		}

		// 将线程信息写入到文件中便于分析
		FileWriter fWriter = new FileWriter("./log.txt");
		PrintWriter pWriter = new PrintWriter(fWriter);

		// 循环遍历获取线程的信息
		for (int i = 0; i < 10; i++) {
			pWriter.println("线程 " + i + " 状态：" + taskArr[i].getState());

			// 将当前线程状态保存到状态数组中
			threadStates[i] = taskArr[i].getState();
		}

		// 启动线程
		for (int i = 0; i < 10; i++) {
			taskArr[i].start();
		}

		// 在运行过程中如果线程的状态和初始状态不一样就将状态变化过程写入到文件中
		boolean finish = false;

		while (!finish) {
			for (int i = 0; i < 10; i++) {
				// 线程状态发生变化
				if (taskArr[i].getState() != threadStates[i]) {
					// 打印线程当前信息
					printThreadMsg(pWriter, taskArr[i], threadStates[i]);

					// 将当前线程状态保存到线程状态数组中
					threadStates[i] = taskArr[i].getState();
				}
			}
			finish = true;
			for (int i = 0; i < 10; i++) {
				finish = finish && (taskArr[i].getState() == State.TERMINATED);
			}
		}
	}

	/**
	 * 打印当前线程的信息
	 * 
	 * @param pWriter
	 * @param thread
	 * @param state
	 */
	private static void printThreadMsg(PrintWriter pWriter, Thread thread, State state) {
		pWriter.println("*********************************************************");
		pWriter.println("线程ID: " + thread.getId() + " 线程名称:" + thread.getName());
		pWriter.println("线程优先级：" + thread.getPriority());
		pWriter.println("线程过去状态：" + state);
		pWriter.println("线程当前状态：" + thread.getState());
		pWriter.println("*********************************************************");
	}
}
