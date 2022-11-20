package com.my.examples.barrier;

import com.my.util.Sleeper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class Participant extends Thread {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private CyclicBarrier barrier = Action.BARRIER;
    private int cash;
    private int currentLotPrice;

    Participant(int cash, int startPrice) {
        this.cash = cash;
        this.currentLotPrice = startPrice;
    }

    @Override
    public void run() {
        log.info("Participant {} specifies a price (cash = {}).", getId(), cash);
        Sleeper.sleep(2);
        int delta = RANDOM.nextInt(20);
        currentLotPrice += delta;
        log.info("Auction participant id = {} - {}", getId(), currentLotPrice);
        try {
            this.barrier.await();
            log.info("Auction participant id = {} continue to work (cash = {})", getId(), cash);
        } catch (InterruptedException | BrokenBarrierException e) {
            log.warn("Interrupted by : ", e);
            Thread.currentThread().interrupt();
        }
    }
}
