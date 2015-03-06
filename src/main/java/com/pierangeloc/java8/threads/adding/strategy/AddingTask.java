package com.pierangeloc.java8.threads.adding.strategy;

import java.util.concurrent.RecursiveTask;


public class AddingTask extends RecursiveTask<Integer> {

    private int[] array;
    private int from;
    private int to;

    private final int THRESHOLD = 1;

    public AddingTask(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if(to - from <= THRESHOLD) {
            return to - from;
        } else {
            int mid = (to + from) / 2;

            AddingTask left = new AddingTask(array, from, mid);
            left.fork();

            AddingTask right = new AddingTask(array, mid, to);
            int rightComputationResult = right.compute();

            int leftComputationResult = left.join();

            return leftComputationResult + rightComputationResult;
        }

    }
}
