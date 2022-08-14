package com.sherlocky.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @date 2022/8/14
 */
public class SortUtilsTest {

    @Test
    public void test() {
        List<Long> userIds = Lists.newArrayList(102L, 103L, 101L);

        List<User> users = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i++) {
            User user = new User(userIds.get((i + 2) % 3), RandomStringUtils.randomAlphanumeric(10));
            users.add(user);
        }
        System.out.println("原User List排序：");
        System.out.println(JsonUtils.toJSONString(users));

        System.out.println("userIds排序：");
        System.out.println(JsonUtils.toJSONString(userIds));

        List<User> sortedUsers = SortUtils.sortByProvideFieldOrder(users, userIds, User::getId);
        System.out.println("User List排序后：");
        System.out.println(JsonUtils.toJSONString(sortedUsers));

        users.remove(0);
        List<User> sortedUsers2 = SortUtils.sortByProvideFieldOrder(users, userIds, User::getId, false);
        System.out.println("User List 删除一个元素，排序后：");
        System.out.println(JsonUtils.toJSONString(sortedUsers2));
    }

    public class User {
        private Long id;
        private String name;

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
