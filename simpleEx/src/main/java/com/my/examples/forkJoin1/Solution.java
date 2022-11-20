package com.my.examples.forkJoin1;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        List<Long> intList = LongStream.range(0, 1000000).boxed().toList();
        ForkJoinTask<Long> task = new SumRecursiveTask(intList);
        long result = new ForkJoinPool().invoke(task);
        log.info("Solution: {}", result);
    }
}
