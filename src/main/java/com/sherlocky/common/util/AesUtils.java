package com.sherlocky.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

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
 */
@Slf4j
public final class AesUtils {
    /** AES 算法 */
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
    public static byte[] encrypt(String content, String encryptKey) {
        try {
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, aesKey(encryptKey), aesIv(encryptKey));
            //根据密码器的初始化方式--加密：将数据加密
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("$$$ AES加密失败", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 密文
     * @param decryptKey 密钥（长度必须为16个字节）
     * @return java.lang.String
     */
    public static byte[] decrypt(String content, String decryptKey) {
        try {
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, aesKey(decryptKey), aesIv(decryptKey));
            // 解密
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
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

    //TODO 加密后通常配合 base64 或者 Hex 编码

    /**
     * 加密后以Base64编码
     * @param content
     * @param encryptKey
     * @return
     */
    public static String encryptBase64(String content, String encryptKey) {
        return CryptoUtils.encodeBase64(encrypt(content, encryptKey));
    }

    /**
     * 加密后转为16进制形式
     * @param content
     * @param encryptKey
     * @return
     */
    public static String encryptHex(String content, String encryptKey) {
        return new String(new Hex(StandardCharsets.UTF_8).encode(encrypt(content, encryptKey)), StandardCharsets.UTF_8);
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
