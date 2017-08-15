package org.learn.java.concurrent.design_pattern.chapter16;

import java.io.IOException;

public class AppServerClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer server = new AppServer(13345);
        server.start();
        Thread.sleep(15_000L);
        server.shutdown();
    }
}
