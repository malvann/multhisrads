package com.my.examples.recursiveAction;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        List<Double> dList = IntStream.range(0, 1000000).asDoubleStream().boxed().parallel().toList();
        new UnaryAction<>(dList, Math::sqrt).invoke();
        dList.forEach(d -> log.info("{}, ", d));
    }
}
