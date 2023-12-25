package com.sherlocky.common.util;

import com.sherlocky.common.strategy.DefaultStringStrategyRunnerImpl;
import org.junit.Test;

/**
 * @author: zhangcx
 * @date: 2024/1/6 15:05
 * @since:
 */
public class StrategyTest {

    @Test
    public void test() {

        DefaultStringStrategyRunnerImpl stringStrategyRunner = new DefaultStringStrategyRunnerImpl();
        stringStrategyRunner.run("default", "I'm default task.");

    }

}
