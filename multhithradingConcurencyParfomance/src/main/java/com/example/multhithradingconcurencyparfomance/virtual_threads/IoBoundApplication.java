package com.example.multhithradingconcurencyparfomance.virtual_threads;

import com.example.multhithradingconcurencyparfomance.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class IoBoundApplication {
    private static final int NUMBER_OF_TASKS = 10_000;

    public void runApp() {
        System.out.printf("Running %d tasks%n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete%n", System.currentTimeMillis() - start);
    }

    private void performTasks() {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(() -> IntStream.range(0, 100).forEach(ind -> blockingIoOperation()));
            }
        }
    }

    private void blockingIoOperation() {
        System.out.println("Executing a blocking task from thread: " + Thread.currentThread());
        Sleeper.sleep(10);
    }
}
