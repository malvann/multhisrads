package com.example.multhithradingconcurencyparfomance.virtual_threads;

public class CommonThreadDemo {
    public void runTest() throws InterruptedException {
        Thread thread;
        int i = 1000;
        while (i-- > 0) {
//        thread = new Thread(() -> System.out.println(Thread.currentThread()));
//        thread = Thread.ofPlatform().unstated(() -> System.out.println(Thread.currentThread()));
            thread = Thread.ofVirtual().unstarted(() -> System.out.println(Thread.currentThread()));
            thread.start();
            thread.join();
        }
    }
}
