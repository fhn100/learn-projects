package org.learn.java.java8.map;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map<String, Integer> wordCount = new HashMap<>();
        String word = "a";
        countWord(word, wordCount);
        word = "a";
        countWord(word, wordCount);
        word = "b";
        countWord(word, wordCount);
        word = "c";
        countWord(word, wordCount);
        wordCount.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    private static void countWord(String word, Map<String, Integer> wordCount) {
        Integer merge = wordCount.merge(word, 1, (oldValue, value) -> oldValue + value);
        System.out.println(merge);
    }
}
