package com.my.examples.lock.reentrant;

import com.my.util.Sleeper;

public class Solution {
    public static void main(String[] args) {
        PaymentLock paymentLock = new PaymentLock();
        new Thread(() -> paymentLock.doPayment(100)).start();
        Sleeper.sleep(2);
        paymentLock.init(120);
    }
}
