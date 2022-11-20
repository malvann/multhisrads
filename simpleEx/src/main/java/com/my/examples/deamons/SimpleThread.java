package com.my.examples.deamons;

import com.my.util.Sleeper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleThread extends Thread {
    int sleepSec;

    @Override
    public void run() {
        if (isDaemon()) {
            log.info("Start {} as daemon", this.getName());
        } else log.info("Start {}", this.getName());
        Sleeper.sleep(sleepSec);
        if (isDaemon()) log.info("Stop {} as daemon", this.getName());
        else log.info("Stop {}", this.getName());
    }
}
