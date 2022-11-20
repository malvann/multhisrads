package com.my.examples.recursiveAction;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.function.UnaryOperator;

@AllArgsConstructor
public class UnaryAction<T> extends RecursiveAction {
    private static final long THRESHOLD = 10000;
    private List<T> subjectList;
    private UnaryOperator<T> operator;

    @Override
    protected void compute() {
        if (subjectList.size() < THRESHOLD) {
            subjectList = subjectList.stream().map(x -> operator.apply(x)).toList();
            return;
        }
        int middle = subjectList.size() / 2;
        invokeAll(new UnaryAction<>(subjectList.subList(0, middle), operator),
                new UnaryAction<>(subjectList.subList(middle, subjectList.size()), operator));
    }
}
