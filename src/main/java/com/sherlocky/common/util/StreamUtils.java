package com.sherlocky.common.util;

import org.checkerframework.checker.units.qual.K;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新增Java Stream工具类
 * @author zhangxu
 * @date 2023/9/27
 */
public class StreamUtils {

    /**
     * 合并List
     * @param list
     * @return
     * @param <E>
     */
    public <E> List<E> concatList(List<E>... list) {
        return Stream.of(list).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * list分组
     * @param list
     * @param keyFunction
     * @return
     * @param <K>
     * @param <E>
     */
    public <K, E> Map<K, List<E>> toListMap(List<E> list, Function<E, K> keyFunction) {
        return list.stream().collect(Collectors.groupingBy(keyFunction));
    }

}
