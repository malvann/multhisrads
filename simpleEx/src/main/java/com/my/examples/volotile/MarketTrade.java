package com.my.examples.volotile;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class MarketTrade implements Runnable {
    private static int THREAD_NUM = 0;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final Market market;
    private final String name;
    private final int maxBoundOfItems;
    private int removedCupsByTrade;
    private int removedPlatesByTrade;
    private int addedCupsByTrade;
    private int addedPlatesByTrade;

    MarketTrade(Market market, int maxBoundOfItems) {
        this.market = market;
        this.name = "THREAD-" + THREAD_NUM++;
        this.maxBoundOfItems = maxBoundOfItems;
    }

    @Override
    public void run() {
        log.info("crockery market {} stats work", name);
        removedCupsByTrade = 0;
        removedPlatesByTrade = 0;
        addedCupsByTrade = 0;
        addedPlatesByTrade = 0;
        int actionNum = random.nextInt(1, 10 + 1);
        log.info("actionNum = {}", actionNum);
        while (actionNum > 0) {
            actionNum--;
            int num = random.nextInt(1, maxBoundOfItems + 1);
            if (doOrNot()) {
                if (doOrNot()) {
                    market.addCups(num);
                    addedCupsByTrade += num;
                    log.info("{} - add {} cups", name, num);
                } else {
                    market.removeCups(num);
                    removedCupsByTrade += num;
                    log.info("{} - remove {} cups", name, num);
                }
            } else {
                if (doOrNot()) {
                    market.addPlates(num);
                    addedPlatesByTrade += num;
                    log.info("{} - add {} plates", name, num);
                } else {
                    market.removePlates(num);
                    removedPlatesByTrade += num;
                    log.info("{} - remove {} plates", name, num);
                }
            }
            log.info(name + " " + market);
            Sleeper.sleep(0);
        }
        log.info(getTurn());
    }

    private boolean doOrNot() {
        return (random.nextInt() & 1) != 0;
    }

    private String getTurn() {
        return String.join("\n", "------------------------------------------",
                name,
                "removed cups = " + removedCupsByTrade,
                "removed plates = " + removedPlatesByTrade,
                "added cups = " + addedCupsByTrade,
                "added plates = " + addedPlatesByTrade,
                market.toString(),
                "---------------------",
                "plates = " + (addedPlatesByTrade - removedPlatesByTrade),
                "cups = " + (addedCupsByTrade - removedCupsByTrade),
                "------------------------------------------");
    }
}
