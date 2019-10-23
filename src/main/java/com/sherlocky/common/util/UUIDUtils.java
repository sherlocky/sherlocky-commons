package com.sherlocky.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * UUID 工具类
 */
public final class UUIDUtils {

    private UUIDUtils() {
    }

    public static String get() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.get());
        System.out.println(UUIDUtils.get());
    }
}
