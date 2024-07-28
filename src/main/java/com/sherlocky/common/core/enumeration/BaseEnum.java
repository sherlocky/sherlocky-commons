package com.sherlocky.common.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sherlocky.common.util.MapUtils;

/**
 * 枚举类型基类
 */
public interface BaseEnum extends IEnum<String> {
    /**
     * 将制定的枚举集合转成 map
     * key -> name
     * value -> desc
     *
     * @param list
     * @return
     */
    static Map<String, String> getMap(BaseEnum[] list) {
        return MapUtils.uniqueIndex(Arrays.asList(list), BaseEnum::getCode, BaseEnum::getDesc);
    }

    /**
     * 编码重写
     *
     * @return
     */
    default String getCode() {
        return toString();
    }

    /**
     * 描述
     *
     * @return
     */
    String getDesc();

    /**
     * 枚举值
     *
     * @return
     */
    @Override
    @JsonIgnore
    default String getValue() {
        return getCode();
    }
}
