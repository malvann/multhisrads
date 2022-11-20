package com.my.examples.runnable;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class PrintNumbersThread extends Thread {
    private static final Random RANDOM = new Random();
    private final int min;
    private final int max;

    public PrintNumbersThread(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {
        int counter = 0;
        while (counter < max) {
            log.info("Num: {}", RANDOM.nextInt((max - min) + 1) + min);
            counter++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.warn("Interrupted: ", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        log.info("{} ends work.", this.getClass().getSimpleName());
    }
}
