package com.sherlocky.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * UUID 工具类
 * @since 0.0.7
 */
public final class UUIDUtils {

    private UUIDUtils() {
    }

    public static String get() {
        return UUIDUtils.get(true);
    }
    
    public static String get(boolean isSimple) {
        String uuid = UUID.randomUUID().toString();
        return isSimple ? StringUtils.replace(uuid, "-", "") : uuid;
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.get());
        System.out.println(UUIDUtils.get(false));
    }
}
