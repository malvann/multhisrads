package com.my.examples.complitableFuture;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Extra {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> errors = new ArrayList<>();

        Future<Person.PersonBuilder> task =
                CompletableFuture.supplyAsync(() -> setName(Person.builder()))
                        .handle((b, e) -> {
                            errors.add(e.getMessage());
                            return b;
                        })
                        .thenCompose(b -> CompletableFuture.supplyAsync(() -> setAge(b)))
                        .handle((b, e) -> {
                            errors.add(e.getMessage());
                            return b;
                        });
        if (task.isDone()){
            if (errors.size() > 1) throw new IllegalArgumentException(errors.toString());
            Person person = task.get().address("0123, Fog st.").build();
            System.out.println(person);
        }

    }

    private static Person.PersonBuilder setName(Person.PersonBuilder builder){
//        throw new IllegalArgumentException("msg1");
        builder.name("Bob");
        return builder;
    }

    private static Person.PersonBuilder setAge(Person.PersonBuilder builder){
        builder.age(21);
        return builder;
    }
}

@Builder
class Person {
    String name;
    int age;
    String address;
}
