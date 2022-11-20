package com.my.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Sleeper {
    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.warn("Interrupt: ", e);
            Thread.currentThread().interrupt();
        }
    }
}
