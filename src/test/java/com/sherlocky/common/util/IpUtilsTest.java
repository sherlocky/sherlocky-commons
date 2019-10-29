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
}
