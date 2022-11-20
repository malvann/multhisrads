package com.my.examples.complitableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.my.util.Sleeper.sleep;

public class Solution {

    public static void main(String[] args) {
        List<String> strings = List.of("dd", "d", "tre");
        Future<List<Integer>> result = doFuture(strings);//slow

        CompletableFuture<Void> ex1 =
                CompletableFuture.supplyAsync(() -> convertStrings(strings))
                        .thenCompose(list -> CompletableFuture.supplyAsync(() -> list.stream().reduce(Integer::sum)))
                        .thenAccept(s -> System.out.println("Ex1: " + s))
                        .thenRun(() -> System.out.println("Computation finished."));
        CompletableFuture<Void> ex2 =
                CompletableFuture.supplyAsync(() -> "test")
                        .thenCombine(CompletableFuture.supplyAsync(() -> " combine"), (s1, s2) -> s1 + s2)
                        .thenAccept(s -> System.out.println("Ex2: " + s));
        CompletableFuture<Void> ex3 =
                CompletableFuture.supplyAsync(() -> "test")
                        .thenAcceptBoth(//thenAcceptBothAsync
                                CompletableFuture.supplyAsync(() -> "acceptBoth"),
                                (s1, s2) -> System.out.println("Ex3: " + s1 + " - " + s2));

        CompletableFuture<Void> ofAll = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> "1").thenAccept(System.out::println),
                CompletableFuture.supplyAsync(() -> "2").thenAccept(System.out::println),
                CompletableFuture.supplyAsync(() -> "3").thenAccept(System.out::println));
        String combined = Stream.of(//slow
                        CompletableFuture.supplyAsync(() -> "4"),
                        CompletableFuture.supplyAsync(() -> "5"),
                        CompletableFuture.supplyAsync(() -> "6"))
                .map(CompletableFuture::join)
                .collect(Collectors.joining("-"));

        CompletableFuture<Void> withErr = CompletableFuture.supplyAsync(() -> {
                    throw new IllegalArgumentException("oops...");
                })
                .handle((o, e) -> e.getMessage())
                .thenAccept(System.out::println);

        try {
            System.out.println("Result:");
            System.out.println("From future: " + result.get());

            ex1.get();
            ex2.get();
            ex3.get();

            ofAll.get();
            System.out.println("Join results: " + combined);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.exit(0);
    }

    private static Future<List<Integer>> doFuture(List<String> strings) {
        CompletableFuture<List<Integer>> result = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            sleep(3);
            List<Integer> lengthList = convertStrings(strings);
            result.complete(lengthList);
        });

        return result;
    }

    private static List<Integer> convertStrings(List<String> strings) {
        return strings.stream().map(String::length).toList();
    }

}
