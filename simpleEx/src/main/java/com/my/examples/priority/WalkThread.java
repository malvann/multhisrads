package com.my.examples.priority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalkThread extends Thread {
    private int counter;

    public WalkThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            counter++;
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        log.info("{} counter={}", this.getName(), counter);
    }
}
