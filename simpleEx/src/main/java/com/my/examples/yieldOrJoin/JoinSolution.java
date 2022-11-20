package com.my.examples.yieldOrJoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class JoinSolution {
    public static void main(String[] args) {
        Thread x = new CustomThread("X", 10);
        Thread y = new CustomThread("Y", 5);
        x.start();
        y.start();
        try {
            TimeUnit.SECONDS.timedJoin(x, 10);
        } catch (InterruptedException e) {
            log.warn("Interrupt: ", e);
            Thread.currentThread().interrupt();
        }
        log.info("end of {}", Thread.currentThread().getName());
    }
}
