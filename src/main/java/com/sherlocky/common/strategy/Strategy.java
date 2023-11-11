package com.sherlocky.common.strategy;

/**
 * 抽象策略接口
 */
public interface Strategy<T> {

    /**
     * 执行任务
     * @return
     */
    void process(T task);
}
