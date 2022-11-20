package com.my.examples.yieldOrJoin;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YieldSolution {
    public static void main(String[] args) {
        new Thread(() -> {
            log.info("1 start");
            Thread.yield();
            Sleeper.sleep(5);
            log.info("1 end");
        }).start();
        new CustomThread("Y", 10).start();
    }
}
