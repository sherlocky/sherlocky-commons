package com.sherlocky.common.util;

import org.apache.commons.collections4.CollectionUtils;
import java.util.*;
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
    public static <E> List<E> concatList(List<E>... list) {
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
    public static <K, E> Map<K, List<E>> groupByKey(List<E> list, Function<E, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyFunction));
    }

    /**
     * list转Map（value为元素自身）
     * @param list
     * @param keyFunction
     * @return
     * @param <K>
     * @param <E>
     */
    public static <K, E> Map<K, E> toIdentityMap(List<E> list, Function<E, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream()//.collect(Collectors.toMap(keyFunction, Function.identity(), (l, r) -> l));
                .collect(HashMap::new, (HashMap<K, E> m, E e) -> m.put(Optional.ofNullable(e).map(keyFunction).get(), e), HashMap::putAll);
    }

}
