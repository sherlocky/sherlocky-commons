package com.sherlocky.common.util;

import com.google.common.collect.Lists;
import com.sherlocky.common.entity.TestCaseUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamUtilsTest {

    @Test
    public void test() {
        List<Long> userIds = Lists.newArrayList(101L, 102L, 103L);
        List<TestCaseUser> users = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i++) {
            TestCaseUser user = new TestCaseUser(userIds.get(i), RandomStringUtils.randomAlphanumeric(10));
            users.add(user);
        }
        System.out.println("原list：" + JsonUtils.toJSONString(users));

        System.out.println("concatList：" + JsonUtils.toJSONString(StreamUtils.concatList(users, users)));

        System.out.println("按ID分组：" + JsonUtils.toJSONString(StreamUtils.groupByKey(users, TestCaseUser::getId)));
        System.out.println("按name分组：" + JsonUtils.toJSONString(StreamUtils.groupByKey(users, TestCaseUser::getName)));

        System.out.println("toIdentityMap：" + JsonUtils.toJSONString(StreamUtils.toIdentityMap(users, TestCaseUser::getId)));
        System.out.println("toMap：" + JsonUtils.toJSONString(StreamUtils.toMap(users, TestCaseUser::getId, TestCaseUser::getName)));
    }

    @Test
    public void testMapMerge() {
        List<Long> userIds = Lists.newArrayList(101L, 102L, 103L);
        List<TestCaseUser> users = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i++) {
            TestCaseUser user = new TestCaseUser(userIds.get(i), RandomStringUtils.randomAlphanumeric(10));
            users.add(user);
        }
        Map<String, Long> userMap = new HashMap<>();
        users.forEach(u -> userMap.merge(
                u.getName(),
                u.getId(),
                Long::sum));
        System.out.println(JsonUtils.toJSONString(userMap));

    }

}
