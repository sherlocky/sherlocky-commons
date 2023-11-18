package com.sherlocky.common.strategy;

public interface StrategyRunner<T> {
    void run(String flag, T task);
}