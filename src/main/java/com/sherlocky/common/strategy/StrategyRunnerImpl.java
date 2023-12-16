package com.sherlocky.common.strategy;

import java.util.Arrays;
import java.util.List;

/**
 * 默认的策略执行器实现类
 */
public class StrategyRunnerImpl extends AbstractStrategyRunner<String> {

    @Override
    public List<Strategy> getStrategies() {
        return Arrays.asList(new DefaultStrategyImpl());
    }
}