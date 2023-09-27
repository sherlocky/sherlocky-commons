package com.sherlocky.common.util;

import org.apache.commons.collections4.CollectionUtils;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新增Java Stream工具类
 * @author zhangxu
 * @date 2023/4/29
 * @since 0.9.0
 */
public class StreamUtils {

    /**
     * 合并List
     * @param list
     * @return
     * @param <E> list元素泛型
     */
    public static <E> List<E> concatList(List<E>... list) {
        return Stream.of(list).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * list分组
     * @param list
     * @param keyFunction
     * @return
     * @param <K> map key泛型
     * @param <E> list元素泛型
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
     * @param <K> map key泛型
     * @param <E> list元素泛型
     */
    public static <K, E> Map<K, E> toIdentityMap(List<E> list, Function<E, K> keyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        //return list.stream().collect(Collectors.toMap(keyFunction, Function.identity(), (l, r) -> l));
        return list.stream().collect(HashMap::new,
                (HashMap<K, E> m, E e) -> m.put(Optional.ofNullable(e).map(keyFunction).get(), e), HashMap::putAll);
    }

    /**
     * list转Map（可指定value类型）
     * @param list
     * @param keyFunction
     * @param valueFunction
     * @return
     * @param <E> list元素泛型
     * @param <K> map key泛型
     * @param <V> map value泛型
     */
    public static <E, K, V> Map<K, V> toMap(List<E> list, Function<E, K> keyFunction, Function<E, V> valueFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        //return list.stream().collect(Collectors.toMap(keyFunction, valueFunction, (l, r) -> l));
        //这样可以防止value为空时，发生NPE
        return list.stream().collect(HashMap::new,
                (HashMap<K, V> m, E e) -> m.put(keyFunction.apply(e), valueFunction.apply(e)), HashMap::putAll);
    }


    /**
     * 转换List（元素类型可变）
     * @param list
     * @param elementMapFunction
     * @return
     * @param <E> list元素泛型
     * @param <F> list新元素泛型
     */
    public static <E, F> List<F> toList(Collection<E> list, Function<E, F> elementMapFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(elementMapFunction).collect(Collectors.toList());
    }

}
