package com.my.examples.runnable;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        Thread timeThread = new Thread(new PrintTime());
        timeThread.start();
        PrintNumbersThread numbersThread = new PrintNumbersThread(1, 10);
        numbersThread.setDaemon(true);
        numbersThread.start();
        Sleeper.sleep(5);
        timeThread.interrupt();
        Sleeper.sleep(3);
        Thread.currentThread().interrupt();
        log.info("Main ends work.");
    }
}
