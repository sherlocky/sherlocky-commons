package com.sherlocky.common.core.enumeration;

import java.io.Serializable;

/**
 * 自定义枚举接口
 */
public interface IEnum<T extends Serializable> {

    /**
     * 枚举存储值
     */
    T getValue();

}
