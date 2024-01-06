package com.sherlocky.common.util;

import com.google.common.collect.Lists;
import com.sherlocky.common.entity.TestCaseUser;
import com.sherlocky.common.strategy.*;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testOtherStrategy() {
        TestCaseUser u1 = TestCaseUser.of(1L, "用户1", null);
        TestCaseUser u2 = TestCaseUser.of(2L, "用户2", null);


        Strategy<TestCaseUser> usCN = new TestCaseUserStrategyCN();
        Strategy<TestCaseUser> usEN = new TestCaseUserStrategyEN();

        StrategyRunner<TestCaseUser> testCaseUserStrategyRunner = new AbstractStrategyRunner<TestCaseUser>() {
            @Override
            public List<Strategy> getStrategies() {
                return Lists.newArrayList(usCN, usEN);
            }
        };
        testCaseUserStrategyRunner.run("CN", u1);
        testCaseUserStrategyRunner.run("EN", u1);
    }

    @StrategyObject("CN")
    private static class TestCaseUserStrategyCN implements Strategy<TestCaseUser> {
        @Override
        public void process(TestCaseUser task) {
            System.out.println("测试用例用户 - ID:" + task.getId() + ", 姓名:" + task.getName());
        }
    }

    @StrategyObject("EN")
    private static class TestCaseUserStrategyEN implements Strategy<TestCaseUser> {
        @Override
        public void process(TestCaseUser task) {
            System.out.println("TestCaseUser - id:" + task.getId() + ", name:" + task.getName());
        }
    }

}
