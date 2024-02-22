package com.example.multhithradingconcurencyparfomance.semaphore_ex;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLockEx {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final TextBox userTextBox = new TextBox();
    private final TextBox passwordTextBox = new TextBox();

    private String userName = null;
    private String password = null;


    public void login() throws InterruptedException {
        lock.lock();
        try {
            while (userName == null || password == null) condition.await();
        } finally {
            lock.unlock();
        }
        verify();
    }

    public void collectCreds() {
        lock.lock();
        try {
            userName = userTextBox.getTextBox();
            password = passwordTextBox.getTextBox();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private void verify() {
        //TODO
    }

    static class TextBox {
        private String val;

        public String getTextBox() {
            return val;
        }
    }
}
