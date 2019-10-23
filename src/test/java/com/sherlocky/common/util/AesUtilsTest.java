package com.sherlocky.common.util;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * AES 工具类测试
 * @author: zhangcx
 * @date: 2019/10/22 19:36
 */
public class AesUtilsTest {

    @Test
    public void test() {
        String src = "1233";
        String k = "123";

        System.out.println(AesUtils.encryptBase64(src, k));
        System.out.println(CryptoUtils.encodeBase64(AesUtils.encrypt(src, k)));
        System.out.println(AesUtils.encryptHex(src, k));

        byte[] bs = AesUtils.encrypt(src, k);
        System.out.println(new String(new Hex(StandardCharsets.UTF_8).encode(bs), StandardCharsets.UTF_8));
        System.out.println(Hex.encodeHexString(bs));
    }
}
