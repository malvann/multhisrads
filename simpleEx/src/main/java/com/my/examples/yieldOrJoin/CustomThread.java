package com.my.examples.yieldOrJoin;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomThread extends Thread {
    private int sleepTime;

    CustomThread(String name, int sleepSec){
        super(name);
        this.sleepTime = sleepSec;
    }

    @Override
    public void run() {
        log.info("{} start", this.getName());
        Sleeper.sleep(sleepTime);
        log.info("{} stop", this.getName());
    }
}
