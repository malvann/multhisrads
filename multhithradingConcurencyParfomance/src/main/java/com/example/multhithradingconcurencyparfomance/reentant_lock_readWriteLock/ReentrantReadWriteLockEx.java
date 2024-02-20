package com.example.multhithradingconcurencyparfomance.reentant_lock_readWriteLock;

import com.example.multhithradingconcurencyparfomance.util.Sleeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ReentrantReadWriteLockEx {
    public static final int HIGHEST_PRICE = 1000;

    public void run() throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        IntStream.range(0, 100000).forEach(i -> inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE)));

        Thread writer = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
                Sleeper.sleep(10);
            }
        });
        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();
        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });
            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();
        for (Thread reader : readers) {
            reader.start();
        }

        for (Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.printf("Reading took %d ms%n", endReadingTime - startReadingTime);
    }

    public static class InventoryDatabase {
        private final TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            reentrantReadWriteLock.readLock().lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
                Integer toKey = priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) return 0;

                return priceToCountMap.subMap(fromKey, true, toKey, true)
                        .values().stream().reduce(Integer::sum).orElse(0);
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }

        public void addItem(int price) {
            reentrantReadWriteLock.writeLock().lock();
            try {
                priceToCountMap.merge(price, 1, Integer::sum);
            } finally {
                reentrantReadWriteLock.writeLock().unlock();
            }
        }

        public void removeItem(int price) {
            reentrantReadWriteLock.writeLock().lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) priceToCountMap.remove(price);
                else priceToCountMap.put(price, numberOfItemsForPrice - 1);
            } finally {
                reentrantReadWriteLock.writeLock().unlock();
            }
        }
    }
}
