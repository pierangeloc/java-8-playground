package com.pierangeloc.java8.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Comparing performances of concurrent approache
 *
 */
public class Adder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Adder.class);


    public static final Integer MAX = 10_000_000;

    public void addIntegersSingleThreadedLockFree() {
        int i = 0;
        long now = System.currentTimeMillis();

        while (i < MAX) {
            i++;
        }

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", MAX, delta, i));
    }

    private Lock lock = new ReentrantLock();

    public void addIntegersSingleThreadedWithLock() {
        int i = 0;
        long now = System.currentTimeMillis();

        while (i < MAX) {
            lock.lock();
            i++;
            lock.unlock();
        }

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", MAX, delta, i));

    }

    class IntHolder {
        public int value;
    }

    class IntIncrementer implements Callable<IntHolder> {

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

    class LockingIntIncrementer implements Callable<IntHolder> {

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

    class AtomicIntIncrementer implements Callable<Integer> {

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


    public void addIntegersWithFourThreads() throws InterruptedException {
        long now = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        IntHolder intHolder = new IntHolder();
        List<IntIncrementer> incrementers = new LinkedList<>();
        for(int i = 0; i < MAX; i++) {
            incrementers.add(new IntIncrementer(intHolder));
        }

        executorService.invokeAll(incrementers);
        LOGGER.info("Shutdown and await termination");
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", MAX, delta, intHolder.value));
    }

    public void addIntegersWithFourThreadsAndLock() throws InterruptedException {
        long now = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        IntHolder intHolder = new IntHolder();
        List<LockingIntIncrementer> incrementers = new LinkedList<>();
        for(int i = 0; i < MAX; i++) {
            incrementers.add(new LockingIntIncrementer(intHolder));
        }

        executorService.invokeAll(incrementers);

        LOGGER.info("Shutdown and await termination");
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.MINUTES);

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", MAX, delta, intHolder.value));
    }


    public void addIntegersWithFourThreadsAndAtomicInteger() throws InterruptedException {
        long now = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        AtomicInteger atomicInteger = new AtomicInteger();
        List<AtomicIntIncrementer> incrementers = new LinkedList<>();
        for(int i = 0; i < MAX; i++) {
            incrementers.add(new AtomicIntIncrementer(atomicInteger));
        }

        executorService.invokeAll(incrementers);

        LOGGER.info("Shutdown and await termination");
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.MINUTES);

        long delta = System.currentTimeMillis() - now;
        LOGGER.info(String.format("Adding %d integers took %d millis. Result: %d", MAX, delta, atomicInteger.get()));
    }

    public static void main(String[] args) throws Exception {
        Adder adder = new Adder();

        System.out.println(String.format("Incrementing an integer %d times with single threaded loop", MAX));
        adder.addIntegersSingleThreadedLockFree();
//
        System.out.println(String.format("Incrementing an integer %d times with single threaded loop and lock/unlock before/after each incrementation", MAX));
        adder.addIntegersSingleThreadedWithLock();

        System.out.println(String.format("Incrementing an integer %d times with 4 threads, without locking (leads to inconsistent result)", MAX));
        adder.addIntegersWithFourThreads();

        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, with locking (should lead to correct result)", MAX));
        adder.addIntegersWithFourThreadsAndLock();

        LOGGER.info(String.format("Incrementing an integer %d times with 4 threads, with atomic integer(should lead to correct result)", MAX));
        adder.addIntegersWithFourThreadsAndAtomicInteger();

    }

    //TODO: forkjoin, and also akka streams with an underlying actor system with 4 threads

}
