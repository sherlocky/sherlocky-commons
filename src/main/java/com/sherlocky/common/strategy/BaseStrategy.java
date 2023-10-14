package com.sherlocky.common.strategy;

/**
 * 抽象策略接口
 */
public interface BaseStrategy<T> {

    /**
     * 执行任务
     * @return
     */
    boolean process(T task);
}
