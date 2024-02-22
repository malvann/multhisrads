package com.example.multhithradingconcurencyparfomance.ex;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class LockFreeStackTest {
    @Test
    @SneakyThrows
    void test() {
//        StandardStack<Integer> stack = new StandardStack<>(); // 253 165 986 operations were performed in 10 seconds
        LockFreeStack<Integer> stack = new LockFreeStack<>(); // 846 409 230 operations were performed in 10 seconds
        Random random = new Random();

        for (int i = 0; i < 100000; i++) {
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        int pushingThreads = 2;
        int poppingThreads = 2;

        for (int i = 0; i < pushingThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.push(random.nextInt());
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (int i = 0; i < poppingThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.pop();
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(10000);

        System.out.printf("%,d operations were performed in 10 seconds %n", stack.getCounter());
    }

    public static class StandardStack<T> {
        private Node<T> head;
        private int counter = 0;

        public synchronized void push(T value) {
            Node<T> newHead = new Node<>(value, null);
            newHead.setNext(head);
            head = newHead;
            counter++;
        }

        public synchronized T pop() {
            if (head == null) {
                counter++;
                return null;
            }

            T value = head.getVal();
            head = head.getNext();
            counter++;
            return value;
        }

        public int getCounter() {
            return counter;
        }
    }
}
