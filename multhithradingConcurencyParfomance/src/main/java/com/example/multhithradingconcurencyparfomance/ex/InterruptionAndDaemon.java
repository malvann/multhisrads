package com.example.multhithradingconcurencyparfomance.ex;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.LongStream;

@Slf4j
public class InterruptionAndDaemon {
    public static void main(String[] args) {
        Thread th = new Thread(() -> {
            long reduce = LongStream.range(1L, 90000000000000L)
                    .peek(v -> {
                        if (Thread.currentThread().isInterrupted()) {
                            log.info("Interrupted");
                            return;
//                            System.exit(0);//th.setDaemon(true); replacer
                        }
                    }).reduce(Long::sum).orElse(-1);
            log.info("Result: %s".formatted(reduce));
        });
        th.setDaemon(true);
        th.start();
        th.interrupt();
        log.info("main ends");
    }
}
