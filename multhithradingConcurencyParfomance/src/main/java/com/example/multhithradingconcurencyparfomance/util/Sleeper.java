package com.example.multhithradingconcurencyparfomance.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sleeper {
    private Sleeper() {
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.warn("Interrupt: %s".formatted(e.getMessage()));
            Thread.currentThread().interrupt();
        }
    }
}
