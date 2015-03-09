package com.pierangeloc.java8.threads.adding.strategy.callables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by pierangeloc on 3-3-15.
 */
public class VolatileIntIncrementer implements Callable<VolatileIntHolder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolatileIntIncrementer.class);
    private VolatileIntHolder holder;

    public VolatileIntIncrementer(VolatileIntHolder holder) {
        this.holder = holder;
    }

    @Override
    public VolatileIntHolder call() throws Exception {
        synchronized (holder) {
            LOGGER.debug("incrementing");
            holder.value++;
        }
        return holder;
    }

}
