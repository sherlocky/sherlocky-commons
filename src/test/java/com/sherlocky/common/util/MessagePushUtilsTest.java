package com.sherlocky.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * 消息推送工具 测试
 *
 * @author: zhangcx
 * @date: 2019/12/25 19:07
 * @since:
 */
public class MessagePushUtilsTest {
    @Test
    public void testPush() {
        String sckey = "xxxxx";
        String title = "单元测试" + RandomStringUtils.randomAlphanumeric(3);
        String content = "yyyyyy";
        boolean success = MessagePushUtils.push(sckey, title, content);
        System.out.println(String.format("### 推送%s~", success ? "成功" : "失败"));
    }
}
