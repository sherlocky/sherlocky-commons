package com.sherlocky.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


/**
 * AES 双向加密解密工具[AES/ECB/PKCS5Padding (128)]
 * <p>AES加密 CBC模式 PKCS5填充方式</p>
 * <p>密钥长度必须为16个字节(128位)，初始向量长度必须为16个字节</p>
 *
 * @author
 * @since 0.1.0
 */
public final class AesUtils {
    private static final Logger log = LoggerFactory.getLogger(AesUtils.class);
    /**
     * AES 算法
     */
    private static final String AES_ALGORITHM = "AES";
    /**
     * 加密算法/加密模式/填充类型（AES_CBC_PKCS5Padding）
     *  AES: 算法
     *  CBC: 模式，使用CBC模式，需要一个向量iv，可增加加密算法的强度
     *  PKCS5: 填充（补码）方式
     */
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
    // 密钥长度必须为16字节
    private static final int KEY_LEN = 16;

    private AesUtils() {}

    /**
     * 加密（AES_CBC_PKCS5Padding）
     *
     * @param content 明文
     * @param encryptKey 密钥（长度必须为16个字节）
     * @return java.lang.String
     */
    public static String encrypt(String content, String encryptKey) {
        try {
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, aesKey(encryptKey), aesIv(encryptKey));
            //根据密码器的初始化方式--加密：将数据加密
            byte[] encrypt = cipher.doFinal(content.getBytes());
            return CryptoUtils.encodeBase64(encrypt);
        } catch (Exception e) {
            log.error("$$$ AES加密失败", e);
        }
        return null;
    }

    /**
     * 解密
     * <p>注意：加密后的byte数组是不能强制转换成字符串的，换言之：字符串和byte数组在这种情况下不是互逆的</p>
     * @param content 密文
     * @param decryptKey 密钥（长度必须为16个字节）
     * @return java.lang.String
     */
    public static String decrypt(String content, String decryptKey) {
        try {
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, aesKey(decryptKey), aesIv(decryptKey));
            // 解密
            byte[] decrypt = cipher.doFinal(CryptoUtils.decodeBase64ToBytes(content));
            return new String(decrypt);
        } catch (NoSuchAlgorithmException e) {
            log.error("$$$ 没有指定的加密算法", e);
        } catch (IllegalBlockSizeException e) {
            log.error("$$$ 非法的块大小", e);
        } catch (NullPointerException e) {
            log.error("$$$ 秘钥解析空指针异常", e);
        } catch (Exception e) {
            log.error("$$$ 秘钥AES解析出现未知错误", e);
        }
        return null;
    }

    /**
     * 密钥长度不足时补"0"
     */
    private static byte[] keyHandler(String key) {
        if (key == null) {
            key = StringUtils.EMPTY;
        }
        key = StringUtils.rightPad(key, 16, "0");
        return key.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 创建AES的密钥(长度必须为16个字节)
     * @param key
     * @return
     */
    private static SecretKeySpec aesKey(String key) {
        return new SecretKeySpec(keyHandler(key), AES_ALGORITHM);
    }

    /**
     * 初始向量
     * @param key
     * @return
     */
    private static IvParameterSpec aesIv(String key) {
        return new IvParameterSpec(keyHandler(key));
    }
}
