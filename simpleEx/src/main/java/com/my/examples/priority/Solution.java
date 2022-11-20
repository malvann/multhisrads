package com.my.examples.priority;

import com.my.util.Sleeper;

public class Solution {
    public static void main(String[] args) {
        WalkThread t1 = new WalkThread("MAX_PRIORITY");
        WalkThread t2 = new WalkThread("MIN_PRIORITY");
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        Sleeper.sleep(5);
        t1.interrupt();
        t2.interrupt();
    }
}
