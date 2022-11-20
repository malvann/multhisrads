package com.my.examples.executorService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class ActionCallable implements Callable<Integer> {
    private List<Integer> intList;

    @Override
    public Integer call() throws Exception {
//        return intList.stream().mapToInt(x -> x).sum();
        return intList.stream().reduce(0, Integer::sum);
    }
}
