package com.my.examples.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerConsumer {
    private boolean ready;

    public synchronized void consume() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.warn("Interrupted by: ", e);
                Thread.currentThread().interrupt();
            }
        }
        ready = false;
    }

    public synchronized void producer() {
        ready = true;
        notifyAll();
    }
}
