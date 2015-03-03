package com.pierangeloc.java8.threads.adding.strategy.callables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by pierangeloc on 3-3-15.
 */
public class LockingIntIncrementer implements Callable<IntHolder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockingIntIncrementer.class);
    private IntHolder holder;

    public LockingIntIncrementer(IntHolder holder) {
        this.holder = holder;
    }

    @Override
    public IntHolder call() throws Exception {
        synchronized (holder) {
            LOGGER.debug("incrementing");
            holder.value++;
        }
        return holder;
    }

}
