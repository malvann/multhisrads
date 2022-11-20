package com.my.examples.executorService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        List<Integer> intList = IntStream.range(1, 1000).boxed().toList();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new ActionCallable(intList));
        executorService.shutdown();
        try {
            log.info("Solution: {}", future.get());
        } catch (ExecutionException | InterruptedException e) {
            log.warn("", e);
            Thread.currentThread().interrupt();
        }
    }
}
