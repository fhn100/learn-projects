package org.learn.java.concurrent.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TranditionTimer1 {

    static class TimeTask1 extends TimerTask {
        @Override
        public void run() {
            System.out.println("2秒炸一次");
            new Timer().schedule(new TimeTask2(), 4000);
        }
    }

    static class TimeTask2 extends TimerTask {
        @Override
        public void run() {
            System.out.println("4秒炸一次");
            new Timer().schedule(new TimeTask1(), 2000);
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimeTask1(),0);
    }
}
