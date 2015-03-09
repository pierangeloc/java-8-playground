package com.pierangeloc.java8.threads;

import com.pierangeloc.java8.threads.adding.strategy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Comparing performances of concurrent approaches
 *
 */
public class Adder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Adder.class);

    public static final Integer MAX = 10_000_000;

    public static void main(String[] args) throws Exception {
        StrategyContext context = new StrategyContext(new SingleThreadedLockFreeStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with single threaded loop", MAX));
        context.add(MAX);


        context = new StrategyContext(new SingleThreadedWithLockStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with single threaded loop and lock/unlock before/after each incrementation", MAX));
        context.add(MAX);

        context = new StrategyContext(new SingleThreadedWithSynchronizedStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with single threaded loop and synchronized around each incrementation", MAX));
        context.add(MAX);

        context = new StrategyContext(new MultiThreadedUnlockedStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, without locking (leads to inconsistent result)", MAX));
        context.add(MAX);

        context = new StrategyContext(new MultiThreadedLockedStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, with locking (should lead to correct result)", MAX));
        context.add(MAX);


        context = new StrategyContext(new MultiThreadedAtomicStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, with atomic integer(should lead to correct result)", MAX));
        context.add(MAX);

        context = new StrategyContext(new MultiThreadedVolatileStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, with volatile integer(should lead to correct result)", MAX));
        context.add(MAX);


        context = new StrategyContext(new MultiThreadedForkJoinStrategy());
        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, with Forkjoin(should lead to correct result)", MAX));
        context.add(MAX);

    }

    //TODO: forkjoin, and also akka streams with an underlying actor system with 4 threads

}
