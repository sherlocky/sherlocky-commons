package com.sherlocky.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author: zhangcx
 * @date: 2019/8/14 17:09
 */
public class CryptoUtilsTest {

    @Test
    public void testBase64() {
        String src = "中国Nb666";
        String srcBase64 = CryptoUtils.encodeBase64(src);
        System.out.println(src);
        System.out.println(srcBase64);
        Assert.assertEquals(srcBase64, "5Lit5Zu9TmI2NjY=");
        Assert.assertEquals(src, CryptoUtils.decodeBase64(srcBase64));
    }

    @Test
    public void testSHA() {
        String src = "中国Nb666";
        System.out.println(src);
        String sha512 = "57cc564cf34bdb9a4a4d3612bd193ea1eead2444924941abaacc1528a0b50df92bf9c2782de83469d6a72d15d6b315ea72c137f9a3d2b531470ae46e794d4948";
        String srcSHA512 = CryptoUtils.encodeSHA512(src);
        System.out.println(srcSHA512);
        Assert.assertEquals(srcSHA512, sha512);

        String sha1 = "50dd851077968cfad9e502939d926301f26ca2c2";
        String srcSHA1 = CryptoUtils.encodeSHA1(src.getBytes());
        System.out.println(srcSHA1);
        Assert.assertEquals(srcSHA1, sha1);
    }

    @Test
    public void testCrc32() {
        File f = new File("E:\\IDEAProjects\\sherlocky-commons\\logo.png");
        String crc32 = "34b09a19";
        String fileSrc32 = CryptoUtils.crc32(f);
        System.out.println(fileSrc32);
        Assert.assertEquals(crc32, fileSrc32);
    }

    @Test
    public void testAsciiRestore2ChineseCharacter() {
        String str = "日志配置";
        String asciiStr = "\\u65E5\\u5FD7\\u914D\\u7F6E";// ASCII码
        String cc = CryptoUtils.asciiRestore2ChineseCharacter(asciiStr);
        System.out.println(cc);
        Assert.assertEquals(str, cc);
    }
}
