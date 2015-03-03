package com.pierangeloc.java8.threads.adding.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingleThreadedWithLockStrategy implements AddingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadedWithLockStrategy.class);

    private Lock lock = new ReentrantLock();

    @Override
    public int increase(int times) {
        int i = 0;
        long now = System.currentTimeMillis();

        while (i < times) {
            lock.lock();
            i++;
            lock.unlock();
        }

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", times, delta, i));
        return i;
    }

}
