package com.pierangeloc.java8.threads.adding.strategy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyContext.class);
    AddingStrategy strategy;

    public StrategyContext(AddingStrategy strategy) {
        this.strategy = strategy;
    }

    public int add(int times) {
        int result;
        try{
            result = this.strategy.increase(times);
        } catch (Exception e) {
            LOGGER.info("Exception!", e);
            result = 0;
        }

        return result;
    }
}
