package com.example.multhithradingconcurencyparfomance.ex;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@Slf4j
public class JoiningTest {

    public static void main(String[] args) throws InterruptedException {
        List<FactorialThread> threads = new ArrayList<>(LongStream.of(1L, 2009876879385045760L, 9L, 3L, 9L)
                .mapToObj(FactorialThread::new)
                .toList());
        for (FactorialThread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }
        for (FactorialThread thread : threads) {
            thread.join(100);
        }

        for (FactorialThread th : threads) {
            if (th.isComplete()) log.info("Result: %s".formatted(th.getRes()));
            else log.info("%s: result is not complete yet".formatted(th.getName()));
        }
    }
}
