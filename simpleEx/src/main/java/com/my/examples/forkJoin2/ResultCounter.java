package com.my.examples.forkJoin2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

public class ResultCounter {
    private static final int BATCH_SIZE = 1000000;
    private final List<Task> tasks = new ArrayList<>();

    public Long getResult(long to) {
        generateTasks(1, to);
        return ForkJoinTask.invokeAll(tasks)
                .parallelStream()
                .mapToLong(ForkJoinTask::join)
                .sum();
    }

    private void generateTasks(long from, long to) {
        if (to - from < BATCH_SIZE) tasks.add(new Task(from, to));
        else {
            tasks.add(new Task(from, from + BATCH_SIZE));
            generateTasks(from + BATCH_SIZE, to);
        }
    }
}
