package com.pierangeloc.java8.threads.adding.strategy.callables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;


public class IntIncrementer implements Callable<IntHolder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntIncrementer.class);
    private IntHolder holder;

    public IntIncrementer(IntHolder holder) {
        this.holder = holder;
    }

    @Override
    public IntHolder call() throws Exception {
        LOGGER.debug("incrementing");
        holder.value++;
        return holder;
    }

}
