package com.sherlocky.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5 工具类
 * need  commons-codec-1.6.jar +
 *
 * @author zhangcx
 * @date 2019-8-4
 */
@Slf4j
public class Md5Utils {
    private static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }

    public static String getFileMD5(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return getFileMD5(new File(filePath));
    }

    /**
     * 对一个文件获取md5值
     *
     * @return md5串
     */
    public static String getFileMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (IOException e) {
            log.error("$$$ 获取文件md5失败！", e);
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流出错！", e);
            }
        }
    }

    /**
     * 求一个字符串的md5值
     *
     * @param target 字符串
     * @return md5 value
     */
    public static String MD5(String target) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(target);
    }
}
