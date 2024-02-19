package com.my.examples.yieldOrJoin;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CustomThread extends Thread {
    private int sleepTime;
    private AtomicInteger integer;

    CustomThread(String name, int sleepSec, AtomicInteger integer){
        super(name);
        this.sleepTime = sleepSec;
        this.integer = integer;
    }

    @Override
    public void run() {
        log.info("{} start", this.getName());
        integer.incrementAndGet();
        Sleeper.sleep(sleepTime);
        log.info("{} stop", this.getName());
    }
}
