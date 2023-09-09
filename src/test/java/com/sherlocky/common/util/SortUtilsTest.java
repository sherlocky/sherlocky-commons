package com.sherlocky.common.util;

import com.google.common.collect.Lists;
import com.sherlocky.common.entity.TestCaseUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2022/8/14
 */
public class SortUtilsTest {

    @Test
    public void test() {
        List<Long> userIds = Lists.newArrayList(102L, 103L, 101L);

        List<TestCaseUser> users = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i++) {
            TestCaseUser user = new TestCaseUser(userIds.get((i + 2) % 3), RandomStringUtils.randomAlphanumeric(10));
            users.add(user);
        }
        System.out.println("原TestCaseUser List排序：");
        System.out.println(JsonUtils.toJSONString(users));

        System.out.println("userIds排序：");
        System.out.println(JsonUtils.toJSONString(userIds));

        List<TestCaseUser> sortedTestCaseUsers = SortUtils.sortByProvideFieldOrder(users, userIds, TestCaseUser::getId);
        System.out.println("TestCaseUser List排序后：");
        System.out.println(JsonUtils.toJSONString(sortedTestCaseUsers));

        users.remove(0);
        List<TestCaseUser> sortedTestCaseUsers2 = SortUtils.sortByProvideFieldOrder(users, userIds, TestCaseUser::getId, false);
        System.out.println("TestCaseUser List 删除一个元素，排序后：");
        System.out.println(JsonUtils.toJSONString(sortedTestCaseUsers2));
    }

}
