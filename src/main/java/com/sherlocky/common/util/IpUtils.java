package com.sherlocky.common.util;

import java.util.regex.Pattern;

/**
 * IP 地址工具类
 * @author: zhangcx
 * @date: 2019/10/29 15:57
 * @since: 0.2.0
 */
public final class IpUtils {
    /** 内网地址 */
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
    /** IP v4 */
    private static final Pattern IPV4 = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    /** IP v6 */
    private static final Pattern IPV6 = Pattern.compile("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");

    private IpUtils() {}

    /**
     * 是否是内网ip地址
     * <p>目前仅支持 ipv4 地址</p>
     * @param ip
     * @return
     */
    public static boolean isInternal(String ip) {
        Asserts.notBlank(ip, "$$$ ip地址不能为空！");
        return isIpv4(ip)
                && INTERNAL_IPS.matcher(ip).matches();
    }

    /**
     * 是否 Ipv4 地址
     * @param ip
     * @return
     */
    public static boolean isIpv4(String ip) {
        Asserts.notBlank(ip, "$$$ ip地址不能为空！");
        return IPV4.matcher(ip).matches();
    }

    /**
     * 是否 Ipv6 地址
     * @param ip
     * @return
     */
    public static boolean isIpv6(String ip) {
        Asserts.notBlank(ip, "$$$ ip地址不能为空！");
        return IPV6.matcher(ip).matches();
    }
}
