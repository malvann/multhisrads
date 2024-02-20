package com.example.multhithradingconcurencyparfomance.reentant_lock_tryLock;

import com.example.multhithradingconcurencyparfomance.util.Sleeper;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class PriceUpdater extends Thread {
    private final Random random = new Random();
    private final PriceContainer priceContainer;

    @Override
    public void run() {
        while (true) {
            priceContainer.getLock().lock();

            try {
                Sleeper.sleep(1000);
                priceContainer.setBitcoinPrice(random.nextInt(20000));
                priceContainer.setEtherPrice(random.nextInt(2000));
                priceContainer.setLiteCoinPrice(random.nextInt(500));
                priceContainer.setBitcoinCashPrice(random.nextInt(5000));
                priceContainer.setRipplePrice(random.nextDouble());
            } finally {
                priceContainer.getLock().unlock();
            }
            Sleeper.sleep(2000);
        }
    }
}
