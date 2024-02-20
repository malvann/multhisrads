package com.example.multhithradingconcurencyparfomance.reentant_lock_tryLock;

public enum Currencies {
    BTC,
    ETH,
    LTC,
    BCH,
    XRP;

    public static Currencies fromName(String name) {
        return Currencies.valueOf(name.toUpperCase());
    }
}
