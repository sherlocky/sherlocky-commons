package com.sherlocky.common.util;

import com.sherlocky.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5 工具类
 * need  commons-codec-1.6.jar +
 *
 * TODO utf常量引用修改
 *
 * @author zhangcx
 * @date 2019-8-4
 */
@Slf4j
public final class Md5Utils {
    /** HmacMD5 加密算法 */
    private static final String ALGORITHM_MAC = "HmacMD5";
    private static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }

    private Md5Utils() {}

    public static String getFileMd5(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return getFileMd5(new File(filePath));
    }

    /**
     * 对一个文件获取md5值
     *
     * @return md5串 (字母小写形式)
     */
    public static String getFileMd5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return Hex.encodeHexString(MD5.digest());
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
     * 计算字符串的md5值
     * 
     * @param target 字符串
     * @return md5串 (字母小写形式)
     */
    public static String md5(final String target) {
        return DigestUtils.md5Hex(target);
    }

    /**
     * 字节数组的的md5值
     *
     * @param bytes 字符串
     * @return md5串 (字母小写形式)
     */
    public static String md5(final byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    /**
     * HmacMD5 加密
     *
     * HMAC(Hash Message Authentication Code)，散列消息鉴别码，基于密钥的Hash算法的认证协议。
     * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
     * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
     *
     * <p>MAC算法可选以下多种算法：</p>
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     *
     * @param source
     * @return hmac md5串 (字母小写形式)
     */
    public static String hmacMd5(String macKey, String source) {
        if (macKey == null || "".equals(macKey)) {
            return null;
        }
        if (source == null || "".equals(source)) {
            return null;
        }
        KeyGenerator keyGen = null;
        Mac mac = null;
        try {
            SecretKey secretKey = new SecretKeySpec(macKey.getBytes(CommonConstants.ENCODING), ALGORITHM_MAC);
            mac = Mac.getInstance(ALGORITHM_MAC);
            mac.init(secretKey);
            mac.update(source.getBytes(CommonConstants.ENCODING));
            // 转成16进制
            return Hex.encodeHexString(mac.doFinal());
        } catch (NoSuchAlgorithmException e) {
            log.error("$$$ 获取 HmaxMD5 key 失败！", e);
        } catch (InvalidKeyException e) {
            log.error("$$$ 无效的 key！", e);
        } catch (UnsupportedEncodingException e) {
            log.error("$$$ 不支持的编码！", e);
        }
        return null;
    }
}
