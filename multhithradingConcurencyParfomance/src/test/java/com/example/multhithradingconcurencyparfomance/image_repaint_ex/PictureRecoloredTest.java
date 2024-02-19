package com.example.multhithradingconcurencyparfomance.image_repaint_ex;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class PictureRecoloredTest {
    @Test
    @SneakyThrows
    void test() {
        new PictureRecolored().colorImage(
                "/Users/marylepskaya/Desktop/many-flowers.jpg",
                "/Users/marylepskaya/Desktop/o.jpg");
    }
}