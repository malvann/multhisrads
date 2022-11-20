package com.my.examples.runnable;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class PrintTime implements Runnable {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm");

    @Override
    public void run() {
        try {
            while (true) {
                log.info("Time: {}", dateFormat.format(new Date()));
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            log.warn("Interrupted: ", e);
            Thread.currentThread().interrupt();
        } finally {
            log.info("{} ends work.", this.getClass().getSimpleName());
        }
    }
}
