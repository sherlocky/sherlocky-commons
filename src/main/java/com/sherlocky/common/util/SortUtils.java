package com.sherlocky.common.util;

import org.apache.commons.collections4.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 常用排序工具类
 * @since 0.8.0
 * @date 2022/8/7
 */
public class SortUtils {

    /**
     * 按照给定字段的顺序排序实体对象
     * @param entityList 实体对象列表
     * @param orderFieldList 已排序的字段值列表
     * @param orderField 排序字段
     * @return 排序后的实体对象列表
     * @param <F> 给定排序的字段类型
     * @param <E> 列表元素类型
     * @since 0.8.0
     */
    public static <F, E> List<E> sortByProvideFieldOrder(List<E> entityList, List<F> orderFieldList, Function<E, F> orderField) {
        return sortByProvideFieldOrder(entityList, orderFieldList, orderField, true);
    }

    /**
     * 按照给定字段的顺序排序实体对象
     * @param entityList 实体对象列表
     * @param orderFieldList 已排序的字段值列表
     * @param orderField 排序字段
     * @param isIgnoreNullElement 是否忽略空值
     * @return 排序后的实体对象列表
     * @param <F> 给定排序的字段类型
     * @param <E> 列表元素类型
     * @since 0.8.0
     */
    public static <F, E> List<E> sortByProvideFieldOrder(List<E> entityList, List<F> orderFieldList, Function<E, F> orderField, boolean isIgnoreNullElement) {
        Map<F, List<E>> entityMapList = entityList.stream().collect(Collectors.groupingBy(orderField));
        return orderFieldList.stream().map(field -> {
            List<E> entityGroupList = entityMapList.get(field);
            if (CollectionUtils.isNotEmpty(entityGroupList)) {
                return entityGroupList.get(0);
            }
            return null;
        }).filter(obj -> !isIgnoreNullElement || obj != null).collect(Collectors.toList());
    }

}
