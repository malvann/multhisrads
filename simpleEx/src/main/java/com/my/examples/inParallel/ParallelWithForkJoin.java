package com.my.examples.inParallel;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

@Slf4j
public class ParallelWithForkJoin {
    public static void main(String[] args) {
        Callable<Integer> task = () -> IntStream.range(0, 100000).boxed()
                .parallel()
                .map(x -> x / 3)
                .peek(x -> log.info(Thread.currentThread().getName()))
                .reduce((x, y) -> x + (int) Math.sqrt(y)).orElse(-1);
        ForkJoinPool pool = new ForkJoinPool(3);
        try {
            log.info("Solution: {}", pool.submit(task).get());
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Catch: ", e);
            Thread.currentThread().interrupt();
        }
    }
}
