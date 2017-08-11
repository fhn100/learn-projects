package org.learn.java.java8.stream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class IntStreamTest {
    public static void main(String[] args) {
        System.out.println("********************************");
        IntStream.range(1, 5).forEach(i -> System.out.println(i));

        System.out.println("********************************");
        IntStream.rangeClosed(1, 5).forEach(i -> System.out.println(i));

        System.out.println("********************************");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        IntStream.range(0, 5).forEach(i -> executor.submit(() -> System.out.println("running task " + i)));
        executor.shutdown();

        System.out.println("********************************");
        int sum = IntStream.iterate(1, i -> i + 3).limit(100).sum();
        System.out.println(sum);
    }
}
