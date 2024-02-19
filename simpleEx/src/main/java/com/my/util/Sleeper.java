package com.my.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Sleeper {
    private Sleeper(){}

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.warn("Interrupt: %s".formatted(e.getMessage()));
            Thread.currentThread().interrupt();
        }
    }
}
