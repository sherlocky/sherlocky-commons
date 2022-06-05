package com.sherlocky.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数组相关工具类
 * @date 2022/5/21
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * 数组是否为空
     * @param <T> 数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组 转换成 List
     * @param array
     * @return
     * @param <T>
     */
    public static <T> List<T> toList(T[] array) {
        if (isEmpty(array)) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.stream(array).collect(Collectors.toList());
    }

}
