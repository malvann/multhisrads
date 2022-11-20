package com.my.examples.semaphore;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
@ToString
public class ChannelPool<T extends Channel> {
    private static final int POOL_SIZE = 5;
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final List<T> resource = new ArrayList<>();

    ChannelPool(List<T> resource) {
        this.resource.addAll(resource);
    }

    public Optional<T> getResource(Client client, long maxWaitSec) throws ResourceException {
        try {
            if (semaphore.tryAcquire(maxWaitSec, TimeUnit.SECONDS)) {
                Optional<T> tOptional = resource.stream().filter(ch -> !ch.isBusy()).findFirst();
                if (tOptional.isPresent()) {
                    Channel ch = tOptional.get();
                    ch.setBusy(true);
                    log.info("{} took channel {}", client, ch);
                }
                return tOptional;
            }
        } catch (InterruptedException e) {
            log.warn("Interrupted by: ", e);
            Thread.currentThread().interrupt();
        }
        throw new ResourceException("Time out: " + maxWaitSec);
    }

    public void releaseSource(Client client, T source) {
        source.setBusy(false);
        log.info("{} - {} --> released", client, source);
    }
}
