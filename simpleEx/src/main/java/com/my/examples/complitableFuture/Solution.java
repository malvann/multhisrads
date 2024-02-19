package com.my.examples.complitableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.my.util.Sleeper.sleep;

@Slf4j
public class Solution {

    public static void main(String[] args) {
        Future<List<Integer>> result = doFuture(List.of("dd", "d", "tre"));//slow

        //   O thenCompose(O)       - do next CF
        //   O thenCombine(O,O)     - do sth with current & previous result
        //void allOf(List<CF>)      - execute CFs
        //void thenAccept(O)        - do sth with O
        //void thenAcceptBoth(O,CF) - do sth with previous result & CF result
        //   O whenComplete(O, Ex)  - do sth with O, handle Ex
        //void thenRun()            - do sth

        CompletableFuture<Void> ex1 =
                CompletableFuture.supplyAsync(() -> "test1")
                        .thenCompose(s -> CompletableFuture.supplyAsync(() -> s.chars().reduce(Integer::sum)))
                        .thenAccept(s -> log.atInfo().log("Ex1: " + s))
                        .thenRun(() -> log.atInfo().log("Ex1 finished"));
        CompletableFuture<String> ex2 =
                CompletableFuture.supplyAsync(() -> "test2")
                        .thenCombine(CompletableFuture.supplyAsync(() -> " combine"), (s1, s2) -> s1 + s2)
                        .whenComplete((s, throwable) -> {
                            if (throwable != null) log.atWarn().log(throwable.getMessage());
                            log.atInfo().log("Ex2: " + s);
                        });
        CompletableFuture<Void> ex3 =
                CompletableFuture.supplyAsync(() -> "test3")
                        .thenAcceptBoth(//thenAcceptBothAsync
                                CompletableFuture.supplyAsync(() -> "acceptBoth"),
                                (s1, s2) -> log.atInfo().log("Ex3: " + s1 + " - " + s2));

        CompletableFuture<Void> ex4 = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> "1").thenAccept(s -> log.atInfo().log("Ex4: " + s)),
                CompletableFuture.supplyAsync(() -> "2").thenAccept(s -> log.atInfo().log("Ex4: " + s)),
                CompletableFuture.supplyAsync(() -> "3").thenAccept(s -> log.atInfo().log("Ex4: " + s)))
                .thenRun(() -> log.atInfo().log("Ex4 finished"));
//        String ex6 = Stream.of(//slow
//                        CompletableFuture.supplyAsync(() -> "4"),
//                        CompletableFuture.supplyAsync(() -> "5"),
//                        CompletableFuture.supplyAsync(() -> "6"))
//                .map(CompletableFuture::join)
//                .collect(Collectors.joining("-"));
//
//        CompletableFuture<Void> ex7 =
//                CompletableFuture.supplyAsync(() -> {
//                            throw new IllegalArgumentException("oops...");
//                        })
//                        .handle((o, throwable) -> {
//                            if (throwable != null) {
//                                log.atWarn().log(throwable.getMessage());
//                                return "FAIL";
//                            }
//                            return "OK";
//                        })
//                        .thenAccept(s -> log.atInfo().log(s));

        try {
//            log.atInfo().log(("From future: " + result.get()));

//            ex1.get();
//            ex2.get();
//            ex3.get();
            ex4.get();
//            ex5.get();
//            log.atInfo().log(("Join results: " + ex6));
//            ex7.get();
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
