package com.sherlocky.common.util;

import java.util.Collection;
import java.util.List;
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

}
