package com.my.examples.deamons;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        SimpleThread normal = new SimpleThread(5);
        SimpleThread daemon = new SimpleThread(10);
        daemon.setDaemon(true);
        daemon.start();
        normal.start();
        log.info("End of main");
    }
}
