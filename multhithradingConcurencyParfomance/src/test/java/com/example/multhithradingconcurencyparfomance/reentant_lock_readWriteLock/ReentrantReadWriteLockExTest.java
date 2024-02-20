package com.example.multhithradingconcurencyparfomance.reentant_lock_readWriteLock;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class ReentrantReadWriteLockExTest {
    @Test
    @SneakyThrows
    void test() {
        new ReentrantReadWriteLockEx().run();
    }

}