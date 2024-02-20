package com.example.multhithradingconcurencyparfomance.reentant_lock_tryLock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor
@Getter
@Setter
public class PriceContainer {
    private final Lock lock = new ReentrantLock();
    private double bitcoinPrice;
    private double etherPrice;
    private double liteCoinPrice;
    private double bitcoinCashPrice;
    private double ripplePrice;

    public String getPrice(Currencies currency) {
        return String.valueOf(
                switch (currency) {
                    case BTC -> bitcoinPrice;
                    case ETH -> etherPrice;
                    case LTC -> liteCoinPrice;
                    case BCH -> bitcoinCashPrice;
                    case XRP -> ripplePrice;
                });
    }
}
