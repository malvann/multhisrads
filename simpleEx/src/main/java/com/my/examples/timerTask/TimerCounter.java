package com.my.examples.timerTask;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

@Slf4j
public class TimerCounter extends TimerTask {
    private int num = 0;

    @Override
    public void run() {
        log.info(String.valueOf(++num));
        Sleeper.sleep(1);
    }
}
