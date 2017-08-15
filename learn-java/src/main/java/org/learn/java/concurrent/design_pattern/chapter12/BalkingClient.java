package org.learn.java.concurrent.design_pattern.chapter12;

public class BalkingClient {
    public static void main(String[] args) {
        BalkingData balkingData = new BalkingData("C:\\Users\\wangwenjun\\IdeaProjects\\java-concurrency\\design_pattern\\balking.txt", "===BEGIN====");
        new CustomerThread(balkingData).start();
        new WaiterThread(balkingData).start();
    }
}