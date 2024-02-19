package com.my.examples.yieldOrJoin;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class YieldSolution {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger integer = new AtomicInteger();
        CustomThread th1 = new CustomThread("th1", 5, integer);
        CustomThread th2 = new CustomThread("th2", 5, integer);
        Thread th3 = new CustomThread2("th3", integer);
        th1.setPriority(5);
        th2.setPriority(5);
        th3.setPriority(5);
        th1.join();
        th2.join();

        th1.start();
        th2.start();
        th3.start();
    }
}

@Slf4j
class CustomThread2 extends Thread {
    private final AtomicInteger integer;

    public CustomThread2(String name, AtomicInteger integer){
        super(name);
        this.integer = integer;
    }

    @Override
    public void run() {
        log.info(getName() + " start");
        integer.incrementAndGet();
        Thread.yield();
        Sleeper.sleep(5);
        log.info(getName() + " end");
    }
}
