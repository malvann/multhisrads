package com.my.examples.forkJoin1;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

@AllArgsConstructor
public class SumRecursiveTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10000;
    private List<Long> intList;

    @Override
    protected Long compute() {
        if (intList.size() <= THRESHOLD) return intList.stream().mapToLong(x -> x).sum();

        int middleIndex = intList.size() / 2;
        SumRecursiveTask rTask1 = new SumRecursiveTask(intList.subList(0, middleIndex));
        SumRecursiveTask rTask2 = new SumRecursiveTask(intList.subList(middleIndex, intList.size()));
//        return rTask1.fork().join() + rTask2.fork().join();
        return Stream.of(rTask1, rTask2).mapToLong(task -> task.fork().join()).sum();
    }
}
