package com.sherlocky.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: zhangcx
 * @date: 2019/10/29 16:06
 * @since: 0.2.0
 */
public class IpUtilsTest {
    @Test
    public void testIsInternal() {
        Assert.assertTrue(IpUtils.isInternal("192.168.12.1"));
        Assert.assertTrue(IpUtils.isInternal("127.168.12.1"));
        Assert.assertTrue(IpUtils.isInternal("10.168.12.1"));
    }

    @Test
    public void testIsNotInternal() {
        Assert.assertFalse(IpUtils.isInternal("114.114.114.114"));
    }

    @Test
    public void testIsIpv4() {
        Assert.assertTrue(IpUtils.isIpv4("114.114.114.114"));
    }

    @Test
    public void testIsIpv6() {
        Assert.assertTrue(IpUtils.isIpv6("CDCD:910A:2222:5498:8475:1111:3900:2020"));
        Assert.assertTrue(IpUtils.isIpv6("1030::C9B4:FF12:48AA:1A2B"));
        Assert.assertTrue(IpUtils.isIpv6("2000:0:0:0:0:0:0:1"));
        Assert.assertTrue(IpUtils.isIpv6("2001:DB8:2de::e13"));
    }
}
