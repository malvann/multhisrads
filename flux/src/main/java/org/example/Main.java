package org.example;

import reactor.core.publisher.Flux;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Flux<String> flux = Flux.fromIterable(List.of("s", "y", "kj")).log();
        flux.subscribe(System.out::println);
    }
}