package com.my.examples.semaphore;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AudioChannel extends Channel {

    @Override
    public void using() {
        log.info("Start using {}", getId());
        Sleeper.sleep(2);
        log.info("End using {}", getId());
    }
}
