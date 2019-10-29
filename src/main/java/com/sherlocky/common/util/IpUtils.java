package com.sherlocky.common.util;

import java.util.regex.Pattern;

/**
 * IP 地址工具类
 * @author: zhangcx
 * @date: 2019/10/29 15:57
 * @since: 0.2.0
 */
public final class IpUtils {
    private static final Pattern INTERNAL_IPS = Pattern.compile(
                    // A 类保留地址：10.0.0.0-10.255.255.255
                    "10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}" +
                    // B 类保留地址：172.16.0.0-172.31.255.255
                    "|172\\.1[6-9]{1}\\.\\d{1,3}\\.\\d{1,3}|172\\.2[0-9]{1}\\.\\d{1,3}\\.\\d{1,3}|172\\.3[0-1]{1}\\.\\d{1,3}\\.\\d{1,3}" +
                    // C 类保留地址：192.168.0.0-192.168.255.255
                    "|192\\.168\\.\\d{1,3}\\.\\d{1,3}" +
                    //  169.254.0.0/16 是一个本地链接地址段 (DHCP分配失败或者没有DHCP服务器时，机器可以自己分配一个IP来完成这个工作)
                    "|169\\.254\\.\\d{1,3}\\.\\d{1,3}" +
                    // 本地软件环回测试（loopback test）
                    "|127\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    ;

    private IpUtils() {}

    /**
     * 是否是内网ip地址
     * @param ip
     * @return
     */
    public static boolean isInternal(String ip) {
        Asserts.notBlank(ip, "$$$ ip地址不能为空！");
        return INTERNAL_IPS.matcher(ip).matches();
    }
}
