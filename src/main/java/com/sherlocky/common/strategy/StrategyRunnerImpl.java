package com.sherlocky.common.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 默认的策略执行器实现类
 */
public class StrategyRunnerImpl implements StrategyRunner<String> {
    private static final List<Strategy> STRATEGIES = Arrays.asList(new DefaultStrategyImpl());
    private static final Map<String, Strategy> STRATEGY_MAP;

    static {
        STRATEGY_MAP = STRATEGIES.stream()
                .collect(Collectors.toMap(
                        s -> s.getClass().getAnnotation(StrategyObject.class).value(),
                        Function.identity()
                ));
    }

    @Override
    public void run(String flag, String task) {
        STRATEGY_MAP.get(flag).process(task);
    }
}