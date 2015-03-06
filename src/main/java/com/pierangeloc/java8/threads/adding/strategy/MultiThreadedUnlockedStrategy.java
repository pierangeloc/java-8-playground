package com.pierangeloc.java8.threads.adding.strategy;

import com.pierangeloc.java8.threads.adding.strategy.callables.IntHolder;
import com.pierangeloc.java8.threads.adding.strategy.callables.IntIncrementer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MultiThreadedUnlockedStrategy implements AddingStrategy {
private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedUnlockedStrategy.class);

    @Override
    public int increase(int times) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        IntHolder intHolder = new IntHolder();
        List<IntIncrementer> incrementers = new LinkedList<>();
        for(int i = 0; i < times; i++) {
            incrementers.add(new IntIncrementer(intHolder));
        }

        long now = System.currentTimeMillis();
        executorService.invokeAll(incrementers);
        LOGGER.info("Shutdown and await termination");
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", times, delta, intHolder.value));
        return intHolder.value;

    }
}
