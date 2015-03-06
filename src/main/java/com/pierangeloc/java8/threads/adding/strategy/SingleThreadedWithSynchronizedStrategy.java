package com.pierangeloc.java8.threads.adding.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleThreadedWithSynchronizedStrategy implements AddingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadedWithSynchronizedStrategy.class);

    private Object lock = new Object();

    @Override
    public int increase(int times) {
        int i = 0;
        long now = System.currentTimeMillis();

        while (i < times) {
            synchronized (lock) {
                i++;
            }
        }

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", times, delta, i));
        return i;
    }

}
