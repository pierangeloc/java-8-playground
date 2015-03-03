package com.pierangeloc.java8.threads.adding.strategy.callables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;


public class AtomicIntIncrementer implements Callable<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtomicIntIncrementer.class);
    private AtomicInteger atomicInteger;

    public AtomicIntIncrementer(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @Override
    public Integer call() throws Exception {
        LOGGER.debug("incrementing");
        return atomicInteger.incrementAndGet();
    }

}

