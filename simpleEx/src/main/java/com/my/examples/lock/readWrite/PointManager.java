package com.my.examples.lock.readWrite;

import com.my.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class PointManager extends Thread {
    private final String threadName;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Point point;
    private final boolean writeStatus;

    PointManager(Point point, boolean writeStatus) {
        this.threadName = this.getName();
        this.point = point;
        this.writeStatus = writeStatus;
    }

    @Override
    public void run() {
        if (writeStatus) randomChangedPoint();
        else distance();
    }

    public void distance() {
        try {
            lock.readLock().lock();
            log.info("{} begin reading: {}", threadName, point);
            Sleeper.sleep(2);
            double distance = Math.hypot(point.getX(), point.getY());
            Sleeper.sleep(2);
            log.info("{} end reading: {}", threadName, String.format("%.2f", distance));
        } finally {
            lock.readLock().unlock();
        }
    }

    public void randomChangedPoint() {
        try {
            lock.writeLock().lock();
            log.info("{} begin writing: {}", threadName, point);
            Sleeper.sleep(2);
            point.setX(ThreadLocalRandom.current().nextInt(10));
            point.setY(ThreadLocalRandom.current().nextInt(10));
            Sleeper.sleep(2);
            log.info("{} end writing: {}", threadName, point);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
