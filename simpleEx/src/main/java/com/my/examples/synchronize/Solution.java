package com.my.examples.synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Solution {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        List<String> list = new ArrayList<>();
        service.submit(new LineAppender("A", list));
        service.submit(new LineAppender("Z", list));
        service.shutdown();
        log.info("Solution builder line: {}", list);
    }
}
