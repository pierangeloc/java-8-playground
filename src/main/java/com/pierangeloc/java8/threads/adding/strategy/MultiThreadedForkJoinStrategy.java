package com.pierangeloc.java8.threads.adding.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;

public class MultiThreadedForkJoinStrategy implements AddingStrategy {
private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedForkJoinStrategy.class);

    @Override
    public int increase(int times) throws InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        int[] ones = new int[times];
        for(int i = 0; i < ones.length; i++) {
            ones[i] = 1;
        }

        long now = System.currentTimeMillis();

        AddingTask mainTask = new AddingTask(ones, 0, ones.length);
        int result = forkJoinPool.invoke(mainTask);

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", times, delta, result));

        return result;
    }
}
