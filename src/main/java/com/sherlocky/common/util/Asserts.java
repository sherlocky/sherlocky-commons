package com.sherlocky.common.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 常用断言 工具类
 *
 * @author: zhangcx
 * @date: 2019/8/26 19:11
 * @since 0.0.6
 */
public final class Asserts {
    /**
     * 防止实例化
     */
    private Asserts() {
    }

    /**
     * 断言一个对象不能为 null
     *
     * @param object  被检查的对象
     * @param message 检查失败后的提示信息
     * @throws IllegalArgumentException 如果对象为 null 则抛出异常
     * @since 0.0.6
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个布尔表达式为true
     *
     * @param expression
     * @param message
     * @throws IllegalArgumentException 如果测试结果为false，则抛出异常
     * @since 0.0.7
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言两个对象是否相等
     * @param left
     * @param right
     * @param message
     * @since 0.0.7
     */
    public static void equals(Object left, Object right, String message) {
        if (!Objects.equals(left, right)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言两个对象是否深度相等（支持数组元素的比较）
     * @param left
     * @param right
     * @param message
     * @since 0.0.7
     */
    public static void deepEquals(Object left, Object right, String message) {
        if (!Objects.deepEquals(left, right)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个字符串不为空
     * @param text
     * @param message
     * @since 0.0.7
     */
    public static void notBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个对象数组不为空
     * @param array
     * @param message
     * @since 0.0.7
     */
    public static void notEmpty(Object[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个byte数组不为空
     * @param array
     * @param message
     * @since 0.0.7
     */
    public static void notEmpty(byte[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个集合不为空
     * @param collection
     * @param message
     * @since 0.0.7
     */
    public static void notEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言Map不能为空
     * @param map
     * @param message
     * @since 0.0.7
     */
    public static void notEmpty(Map map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个对象是否为某个类的子类
     * @param type
     * @param obj
     * @param message
     * @since 0.0.7
     */
    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "要检查的类型不能为空");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个类是否为某个类的父类
     * @param superType
     * @param subType
     * @param message
     * @since 0.0.7
     */
    public static void isAssignable(Class superType, Class subType, String message) {
        notNull(superType, "要检查的类型不能为空");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message);
        }
    }
}
