package com.my.examples.volotile;

import java.util.concurrent.*;

public class Solution {
    public static void main(String[] args) {
        Market market = new Market();
//        ExecutorService service = Executors.newFixedThreadPool(10);
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        service.submit(new MarketTrade(market, 9));
        service.submit(new MarketTrade(market, 3));
        service.shutdown();
    }
}
