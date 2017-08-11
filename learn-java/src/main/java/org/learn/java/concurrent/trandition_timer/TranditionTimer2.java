package org.learn.java.concurrent.trandition_timer;

import java.util.Timer;
import java.util.TimerTask;

public class TranditionTimer2 {
    static int count = 0;

    static class TimeTask extends TimerTask {

        @Override
        public void run() {
            count = (count + 1) % 2;
            System.out.println((count == 0 ? 4 : 2) + "秒炸一次");
            new Timer().schedule(new TimeTask(), 2000 * (count + 1));
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimeTask(), 0);
    }
}
