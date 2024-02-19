package com.my.examples.yieldOrJoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class JoinSolution {
    public static void main(String[] args) {
        Thread th1 = new CustomThread("th1", 2, new AtomicInteger());
        Thread th2 = new CustomThread("th2", 2, new AtomicInteger());
        th1.start();
        try {
            TimeUnit.SECONDS.timedJoin(th1, 10);
        } catch (InterruptedException e) {
            log.warn("Interrupt: ", e);
            Thread.currentThread().interrupt();
        }
        th2.start();
        log.info("end of {}", Thread.currentThread().getName());
    }
}
