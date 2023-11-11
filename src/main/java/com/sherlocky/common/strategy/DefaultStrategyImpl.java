package com.sherlocky.common.strategy;

/**
 * 默认的策略实现类
 */
public class DefaultStrategyImpl implements Strategy<String> {

    @Override
    public void process(String value) {
        System.out.println("DefaultStrategyImpl -- process");
    }

}