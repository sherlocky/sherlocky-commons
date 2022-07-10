package com.sherlocky.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数组相关工具类
 * @date 2022/5/21
 * @since 0.8.0
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

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }

    /**
     * 数组转String
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        }

        if(obj instanceof long[]){
            return Arrays.toString((long[]) obj);
        } else if(obj instanceof int[]){
            return Arrays.toString((int[]) obj);
        } else if(obj instanceof short[]){
            return Arrays.toString((short[]) obj);
        } else if(obj instanceof char[]){
            return Arrays.toString((char[]) obj);
        } else if(obj instanceof byte[]){
            return Arrays.toString((byte[]) obj);
        } else if(obj instanceof boolean[]){
            return Arrays.toString((boolean[]) obj);
        } else if(obj instanceof float[]){
            return Arrays.toString((float[]) obj);
        } else if(obj instanceof double[]){
            return Arrays.toString((double[]) obj);
        } else if (ArrayUtils.isArray(obj)) {
            // 对象数组
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {
                //ignore
            }
        }

        return obj.toString();
    }

}
