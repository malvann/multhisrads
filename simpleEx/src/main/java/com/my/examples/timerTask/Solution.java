package com.my.examples.timerTask;

import java.util.Timer;

public class Solution {
    public static void main(String[] args) {
        new Timer().schedule(new TimerCounter(), 100, 3000);
    }
}
