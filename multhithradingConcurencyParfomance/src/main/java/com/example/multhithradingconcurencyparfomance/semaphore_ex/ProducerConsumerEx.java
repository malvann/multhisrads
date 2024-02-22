package com.example.multhithradingconcurencyparfomance.semaphore_ex;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerEx {
    private final Semaphore produceLock = new Semaphore(0);
    private final Semaphore consumerLock = new Semaphore(3);
    private final Lock itemAccessLock = new ReentrantLock();
    private final Queue<String> items = new ArrayDeque<>();

    public void produce() throws IOException, InterruptedException {
        produceLock.acquire();
        produceItems();
        consumerLock.release();
    }

    public void consume() throws InterruptedException {
        consumerLock.acquire();
        consumeItems();
        produceLock.release();
    }

    private void produceItems() throws IOException {
        itemAccessLock.lock();
        items.add(String.valueOf(System.in.readAllBytes()));
        itemAccessLock.unlock();
    }

    private void consumeItems() {
        itemAccessLock.lock();
        System.out.println(items.peek());
        itemAccessLock.unlock();
    }
}
