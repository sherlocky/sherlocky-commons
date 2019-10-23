package com.sherlocky.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * AES 工具类测试
 * @author: zhangcx
 * @date: 2019/10/22 19:36
 */
public class AesUtilsTest {

    @Test
    public void test() {
        String key = RandomStringUtils.randomAlphanumeric(16);//"1234567890";
        String[] words = {
                "", "123456", "word"
        };
        System.out.println("word | AESEncode | AESDecode");
        for (String word : words) {
            System.out.print(word + " | ");
            String encryptString = AesUtils.encrypt(word, key);
            System.out.print(encryptString + " | ");
            String decryptString = AesUtils.decrypt(encryptString, key);
            System.out.println(decryptString);
        }

        // 单个测试
        String word = "hello";
        Assert.assertEquals(word, AesUtils.decrypt(AesUtils.encrypt(word, key), key));
    }
}
