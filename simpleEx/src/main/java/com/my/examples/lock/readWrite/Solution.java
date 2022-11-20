package com.my.examples.lock.readWrite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Solution {
    public static void main(String[] args) {
        Point point = new Point(3, 3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int counter = 10;
        do {
            counter--;
            executor.submit(new PointManager(point, random.nextBoolean()));
        } while (counter > 0);
        executor.shutdown();
    }
}
