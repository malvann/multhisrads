package com.my.examples.waitNotify;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class PaymentSolution {
    public static void main(String[] args) {
        PaymentAccount account = new PaymentAccount();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int counter = 5;
        do {
            counter--;
            new Thread(() -> account.doPayment(random.nextInt(1000))).start();
            Sleeper.sleep(0);
            account.doDeposit(random.nextInt(1000));
        } while (counter > 0);
    }
}
