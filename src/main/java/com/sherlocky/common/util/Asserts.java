package com.sherlocky.common.util;

import javax.annotation.Nullable;

/**
 * 常用断言 工具类
 * @author: zhangcx
 * @date: 2019/8/26 19:11
 */
public class Asserts {
    /**
     * 断言一个对象不能为 null
     * @param object 被检查的对象
     * @param msg 检查失败后的提示信息
     * @throws IllegalArgumentException 如果对象为 null 则抛出异常
     */
    public static void notNull(@Nullable Object object, String msg) {
        if (object == null) {
            throw new IllegalArgumentException(msg);
        }
    }
}
