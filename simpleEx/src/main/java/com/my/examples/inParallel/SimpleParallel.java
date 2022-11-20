package com.my.examples.inParallel;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.LongStream;

@Slf4j
public class SimpleParallel {
    public static void main(String[] args) {
        Optional<Long> result = LongStream.range(0, 1000).boxed()
                .parallel()
                .map(x -> x / 2)
                .peek(x -> log.info(Thread.currentThread().getName()))
                .reduce((x, y) -> x + (int) (3 * Math.sin(y)));
        log.info("Solution: {}", result.orElse(-1L));
    }
}
