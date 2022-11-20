package com.my.examples.semaphore;

import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        List<Channel> chList = Arrays.asList(new AudioChannel(), new AudioChannel(), new AudioChannel(),
                new AudioChannel(), new AudioChannel());
        ChannelPool<Channel> chPool = new ChannelPool<>(chList);
        int k = 7;
        do {
            new Client(chPool).start();
            k--;
        } while (k > 0);
    }
}
