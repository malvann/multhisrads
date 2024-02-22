package com.example.multhithradingconcurencyparfomance.virtual_threads;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class CommonThreadDemoTest {
    @Test
    @SneakyThrows
    void test() {
        new CommonThreadDemo().runTest();
    }

}