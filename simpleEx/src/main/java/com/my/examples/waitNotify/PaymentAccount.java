package com.my.examples.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentAccount {
    private Integer amount = 0;

    public synchronized void doPayment(int payment) {
        log.info("Start payment: {}", payment);
        try {
            while (amount <= 0) {
                this.wait();
            }
            amount -= payment;
        } catch (InterruptedException e) {
            log.warn("Interrupted by: ", e);
            Thread.currentThread().interrupt();
        }
        log.info("End payment: {}", this.amount);
    }

    public synchronized void doDeposit(int amount) {
        log.info("Start depositing: {}", amount);
        this.amount += amount;
        this.notifyAll();
        log.info("End deposit: {}", this.amount);
    }
}
