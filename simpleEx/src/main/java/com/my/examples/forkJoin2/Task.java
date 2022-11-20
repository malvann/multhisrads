package com.my.examples.forkJoin2;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class Task extends RecursiveTask<Long> {
    long from;  //include
    long to;    //include

    public Task(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    protected Long compute() {
        return LongStream.range(from, to).map(num -> (num ^ to) > to ? 1 : 0).sum();
    }
}
