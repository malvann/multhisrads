package com.example.multhithradingconcurencyparfomance.ex;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class FactorialThread extends Thread {
    private BigInteger res;
    private long base;
    private boolean isComplete;

    public FactorialThread(long base) {
        this.base = base;
    }

    @Override
    public void run() {
        res = BigInteger.valueOf(base);

        while (base-- > 1) {
            res = res.multiply(BigInteger.valueOf(base));
        }
        this.isComplete = true;
    }

    public BigInteger getRes() {
        return res;
    }

    public boolean isComplete() {
        return this.isComplete;
    }
}
