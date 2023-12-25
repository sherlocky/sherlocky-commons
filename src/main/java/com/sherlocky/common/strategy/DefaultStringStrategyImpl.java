package com.sherlocky.common.strategy;

/**
 * 默认的策略实现类
 */
@StrategyObject("default")
public class DefaultStringStrategyImpl implements Strategy<String> {

    @Override
    public void process(String value) {
        System.out.println("DefaultStringStrategyImpl -- process | argument : " + value);
    }

}