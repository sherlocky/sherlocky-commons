package com.sherlocky.common.util;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

/**
 * md5工具类测试
 * @author: zhangcx
 * @date: 2019/8/15 15:37
 */
public class Md5UtilsTest {

    @Test
    public void testFileMd50() {
        File desktop = new File("D:\\Sherlocky\\Desktop\\55\\");
        File baitian = new File("E:\\SynologyDrive\\life\\图片\\米乐\\米乐-百天照-20200727\\");
        Set<String> md5s = Sets.newHashSet();
        Arrays.stream(baitian.listFiles()).forEach(ff -> {
            md5s.add(Md5Utils.getFileMd5(ff));
        });

        Arrays.stream(desktop.listFiles(ff -> ff.isFile())).forEach(ff -> {
            String md5 = Md5Utils.getFileMd5(ff);
            if (!md5s.contains(md5)) {
                System.out.println(ff.getName());
            }
        });
    }

    @Test
    public void testStrMd5() {
        String str = "中国Nb666";
        String md5 = "8ae9e476c872843815e8de86fc537b01";
        String strMd5 = Md5Utils.md5(str);
        System.out.println(strMd5);
        Assert.assertEquals(md5, strMd5);
    }

    @Test
    public void testFileMd5() {
        System.out.println(System.getProperty("user.dir"));
        String md5 = "b733d393dea6ef03994a875106d45656";
        File f = new File(System.getProperty("user.dir") + "/logo.png");
        String fileMd5 = Md5Utils.getFileMd5(f);
        Assert.assertEquals(md5, fileMd5);
    }

    @Test
    public void testHmacMd5() {
        // 原始字符串
        String src = "bishengoffice.com";
        // 毕升文档中给出的加密结果字符串,API key 为的 45ae1f8b5d50ea9322a3d8e3326ca0f9 的情况下。
        String hmacByBiSheng = "a3bcad4f42b7b41edbf7f737bd4d8254";
        System.out.println(src);
        String sign = Md5Utils.hmacMd5("45ae1f8b5d50ea9322a3d8e3326ca0f9", src);
        System.out.println(sign);
        Assert.assertEquals(hmacByBiSheng, sign);
    }
}
