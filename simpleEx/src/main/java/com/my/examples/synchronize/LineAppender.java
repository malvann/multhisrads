package com.my.examples.synchronize;

import java.util.List;

public class LineAppender implements Runnable {
    private final List<String> sList;
    private final String letter;

    LineAppender(String letter, List<String> list) {
        this.letter = letter;
        this.sList = list;
    }

    @Override
    public void run() {
        int n = 0;
        do {
            synchronized (sList) {
                sList.add(letter);
            }
            n++;
        } while (n < 5);
    }
}
