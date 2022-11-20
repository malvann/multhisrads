package com.my.examples.semaphore;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Channel {
    private final String id = UUID.randomUUID().toString();
    private volatile boolean busy;

    public abstract void using();

    @Override
    public String toString() {
        return "Channel: id = " + id + ", isBusy = " + isBusy();
    }
}
