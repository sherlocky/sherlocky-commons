package com.sherlocky.common.strategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抽象策略执行器
 */
public abstract class AbstractStrategyRunner<T> implements StrategyRunner<T> {

    @Override
    public void run(String flag, T task) {
        getStrategyMap().get(flag).process(task);
    }

    public Map<String, Strategy> getStrategyMap() {
        return getStrategies().stream()
                .collect(Collectors.toMap(
                s -> s.getClass().getAnnotation(StrategyObject.class).value(),
                Function.identity()
        ));
    }

    public abstract List<Strategy> getStrategies();

}