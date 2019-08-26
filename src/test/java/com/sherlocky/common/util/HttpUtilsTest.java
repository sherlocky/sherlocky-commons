package com.sherlocky.common.util;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * http 工具类测试
 * @author: zhangcx
 * @date: 2019/8/26 19:41
 */
public class HttpUtilsTest {
    @Test
    public void testDownloadFile1mb() {
        String downloadUrl = "http://cachefly.cachefly.net/1mb.test";
        String fileName = FilenameUtils.getName(downloadUrl);
        String destFilePath = String.format("D:/testDownloadFile_%s", fileName);
        Assert.assertTrue(HttpUtils.downloadFile(downloadUrl, destFilePath));
    }
}
