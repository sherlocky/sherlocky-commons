package com.sherlocky.common.util;

import com.sherlocky.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.Base64;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * 加密工具类，包含BASE64,SHA,CRC32
 * @since 0.0.5
 */
@Slf4j
public final class CryptoUtils {

    private CryptoUtils() {}

    /**
     * SHA1 散列
     *
     * @param bytes an array of byte.
     * @return a {@link String} object.
     */
    public static String encodeSHA1(final byte[] bytes) {
        return DigestUtils.sha1Hex(bytes);
    }

    /**
     * SHA512 散列
     *
     * @param bytes an array of byte.
     * @return a {@link String} object.
     */
    public static String encodeSHA512(final byte[] bytes) {
        return DigestUtils.sha512Hex(bytes);
    }

    /**
     * SHA512 散列
     *
     * @param str     a {@link String} object.
     * @param charset a {@link String} object.
     * @return a {@link String} object.
     */
    public static String encodeSHA512(final String str, final String charset) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes(charset);
            return encodeSHA512(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA512 散列,默认编码 utf-8
     *
     * @param str a {@link String} object.
     * @return a {@link String} object.
     */
    public static String encodeSHA512(final String str) {
        return encodeSHA512(str, CommonConstants.DEFAULT_CHARSET_NAME);
    }

    /**
     * BASE64加密
     *
     * @param bytes an array of byte.
     * @return a {@link String} object.
     */
    public static String encodeBase64(final byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * BASE64加密
     *
     * @param str     a {@link String} object.
     * @param charset a {@link String} object.
     * @return a {@link String} object.
     */
    public static String encodeBase64(final String str, String charset) {
        if (str == null) {
            return null;
        }
        try {
            return encodeBase64(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * BASE64加密,默认UTF-8
     *
     * @param str a {@link String} object.
     * @return a {@link String} object.
     */
    public static String encodeBase64(final String str) {
        return encodeBase64(str, CommonConstants.DEFAULT_CHARSET_NAME);
    }

    /**
     * BASE64 解密，将字符串解密为 byte[]
     * @param content
     * @return
     */
    public static byte[] decodeBase64ToBytes(String content) {
        return Base64.getDecoder().decode(content);
    }

    /**
     * BASE64解密,默认UTF-8
     *
     * @param str a {@link String} object.
     * @return a {@link String} object.
     * @since 0.1.0
     */
    public static String decodeBase64(String str) {
        return decodeBase64(str, CommonConstants.DEFAULT_CHARSET_NAME);
    }

    /**
     * BASE64解密
     *
     * @param str     a {@link String} object.
     * @param charset 字符编码
     * @return a {@link String} object.
     */
    public static String decodeBase64(String str, String charset) {
        try {
            return new String(Base64.getDecoder().decode(str.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算二进制字节校验码
     *
     * @param data 二进制数据
     * @return  校验码（字符串）
     */
    public static String crc32(byte[] data) {
        return crc32(data, 0, data.length);
    }

    /**
     * 计算二进制字节校验码
     *
     * @param data   二进制数据
     * @param offset 起始字节索引
     * @param length 校验字节长度
     * @return 校验码（字符串）
     */
    public static String crc32(byte[] data, int offset, int length) {
        CRC32 crc32 = new CRC32();
        crc32.update(data, offset, length);
        return Long.toHexString(crc32.getValue());
    }

    /**
     * 计算字符串校验码
     *
     * @param str     字符串
     * @param charset 编码
     * @return 校验码（字符串）
     */
    public static String crc32(String str, String charset) {
        try {
            return crc32(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算字符串校验码,默认UTF-8编码
     *
     * @param str     字符串
     * @return 校验码（字符串）
     */
    public static String crc32(String str) {
        return crc32(str, CommonConstants.DEFAULT_CHARSET_NAME);
    }

    /**
     * 对文件内容计算crc32校验码
     *
     * <p>CRC  即循环冗余校验码（Cyclic Redundancy Check）
     * 是数据通信领域中最常用的一种查错校验码，其特征是信息字段和校验字段的长度可以任意选定。</p>
     *
     * @param file 需要计算crc32校验码的文件
     * @return 校验码
     * @throws IOException 读取文件异常
     */
    public static String crc32(File file) {
        try {
            return crc32(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            log.error("$$$$ 计算crc32出错~", e);
        }
        return null;
    }

    /**
     * 对流计算crc32校验码
     *
     * @param input 需要计算crc32校验码的流
     * @return 校验码
     * @throws IOException 读取文件异常
     */
    public static String crc32(InputStream input) {
        if (input == null) {
            return null;
        }
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkInputStream = null;
        byte[] buff = new byte[64 * 1024];
        int len;
        try {
            checkInputStream = new CheckedInputStream(input, crc32);
            while ((len = checkInputStream.read(buff)) != -1) {
            }
            return Long.toHexString(crc32.getValue());
        } catch (IOException e) {
            log.error("$$$$ 计算crc32出错~", e);
        } finally {
            if (checkInputStream != null) {
                try {
                    checkInputStream.close();
                } catch (final IOException ioe) {
                    // ignore
                }
            }
        }
        return null;
    }

    /**
     * 将ascii字符串装为中文
     * @param asciiStr 形如：\u65E5\u5FD7\u914D\u7F6E
     * @return
     */
    public static String asciiRestore2ChineseCharacter(String asciiStr) {
        if (asciiStr == null) {
            return null;
        }
        if (!asciiStr.contains("\\u")) {
            throw new RuntimeException("不合法的ascii字符串");
        }
        String[] chars = asciiStr.split("\\\\u");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < chars.length; i++) {
            sb.append((char) Integer.parseInt(chars[i], 16));
        }
        return sb.toString();
    }
}