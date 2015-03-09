package com.pierangeloc.java8.threads.adding.strategy;

import com.pierangeloc.java8.threads.adding.strategy.callables.VolatileIntHolder;
import com.pierangeloc.java8.threads.adding.strategy.callables.VolatileIntIncrementer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MultiThreadedVolatileStrategy implements AddingStrategy {
private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedVolatileStrategy.class);

    @Override
    public int increase(int times) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        VolatileIntHolder volatileIntHolder = new VolatileIntHolder();
        List<VolatileIntIncrementer> incrementers = new LinkedList<>();
        for(int i = 0; i < times; i++) {
            incrementers.add(new VolatileIntIncrementer(volatileIntHolder));
        }

        long now = System.currentTimeMillis();
        executorService.invokeAll(incrementers);

        LOGGER.info("Shutdown and await termination");
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.MINUTES);

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", times, delta, volatileIntHolder.value));
        return volatileIntHolder.value;
    }
}
