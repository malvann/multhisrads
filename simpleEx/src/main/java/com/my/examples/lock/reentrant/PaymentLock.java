package com.my.examples.lock.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class PaymentLock {
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();
    private int amount = 0;

    public void doPayment(int sum) {
        try {
            log.info("Start payment: {}", -sum);
            lock.lock();
            while (amount <= 0) {
                condition.await();
            }
            amount -= sum;
            log.info("End payment [amount={}]", amount);
        } catch (InterruptedException e) {
            log.warn("Interrupted by: ", e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void init(int sum){
        try {
            lock.lock();
            log.info("Init amount: {}", sum);
            amount += sum;
        } finally {
            condition.signal();
            lock.unlock();
        }
    }
}
