package com.my.examples.exceptionThreads;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        new Thread(() -> {
            if (true) throw new RuntimeException();
            log.info("end of thread");
        }).start();
        Sleeper.sleep(3);
//        if (true) throw new RuntimeException();
        log.info("end of main");
    }
}
