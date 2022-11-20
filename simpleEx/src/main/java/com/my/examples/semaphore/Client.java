package com.my.examples.semaphore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Client extends Thread {
    private final String id = UUID.randomUUID().toString();
    private final ChannelPool<Channel> pool;

    @Override
    public void run() {
        Channel channel = null;
        try {
            Optional<Channel> tOptional = pool.getResource(this, 10);
            if (tOptional.isPresent()) {
                channel = tOptional.get();
                channel.using();
            }
        } catch (ResourceException e) {
            log.warn("Client {} lost -> {}", this.id, e);
        } finally {
            if (channel != null) pool.releaseSource(this, channel);
        }
    }

    @Override
    public String toString() {
        return "Client: " + id;
    }
}
