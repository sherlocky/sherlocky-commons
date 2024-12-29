package com.sherlocky.common.image;

import com.sherlocky.common.constant.StrPool;
import com.sherlocky.common.core.enumeration.BaseEnum;
import com.sherlocky.common.util.StringUtils;

import java.util.Base64;

/**
 * Base图片类型枚举
 */
public enum Base64ImageType implements BaseEnum {
    JPG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    BMP("bmp", "image/bmp"),
    WEBP("webp", "image/webp"),
    ICO("icon", "image/x-icon"),
    TIF("tiff", "image/tiff"),
    SVG("svg", "image/svg+xml"),
    UNKNOWN("", "*");

    private String extension;
    private String contentType;

    Base64ImageType(String desc, String contentType) {
        this.extension = desc;
        this.contentType = contentType;
    }

    @Override
    public String getDesc() {
        return this.extension;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }

    public static Base64ImageType getByCode(String code) {
        for (Base64ImageType t : Base64ImageType.values()) {
            if (t.getCode().equalsIgnoreCase(code)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 消除可能存在的data前缀
     * <p>data:image/png;base64,xxx</p>
     * <p>data:image/jpeg;base64,xxx</p>
     * @return
     */
    public static String trimDataPrefix(String base64Image) {
        if (!StringUtils.contains(base64Image, StrPool.COMMA)) {
            return base64Image;
        }
        return StringUtils.substringAfterLast(base64Image, StrPool.COMMA);
    }

    /**
     * 通过Base编码 识别对应的图片类型
     * <p>每种图片格式都有其特定的文件签名或“魔数”（magic number），即文件的开头几字节，能帮助我们识别文件类型。</p>
     * @param base64Image
     * @return
     */
    public static Base64ImageType detectImageType(String base64Image) {
        // 形如 data:image/jpeg
        for (Base64ImageType bit : Base64ImageType.values()) {
            // 如果 Base64 字符串包含前缀，直接通过前缀判断
            if (base64Image.startsWith("data:" + bit.contentType)) {
                return bit;
            }
        }
        // 去掉可能存在的数据 URI 前缀，形如：data:image/jpeg;base64,/9j/xxx
        base64Image = trimDataPrefix(base64Image);

        // 解码 Base64 字符串
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // 检查魔数字节
        /**
         * 常见图片格式的魔数字节（十六进制表示）：
         *
         * JPEG: FF D8 FF（Base64 编码后为 /9j/）
         * PNG: 89 50 4E 47（Base64 编码后为 iVBORw0KGgo=）
         * GIF: 47 49 46 38（Base64 编码后为 R0lGODdh 或 R0lGODlh）
         * BMP: 42 4D（Base64 编码后为 Qk0=）
         */
        if (imageBytes.length > 4) {
            if ((imageBytes[0] & 0xFF) == 0xFF && (imageBytes[1] & 0xFF) == 0xD8) {
                return Base64ImageType.JPG;
            }
            if ((imageBytes[0] & 0xFF) == 0x89 && (imageBytes[1] & 0xFF) == 0x50) {
                return Base64ImageType.PNG;
            }
            if ((imageBytes[0] & 0xFF) == 0x47 && (imageBytes[1] & 0xFF) == 0x49 && (imageBytes[2] & 0xFF) == 0x46) {
                return Base64ImageType.GIF;
            }
            if ((imageBytes[0] & 0xFF) == 0x42 && (imageBytes[1] & 0xFF) == 0x4D) {
                return Base64ImageType.BMP;
            }
            if ((imageBytes[0] & 0xFF) == 0x00 && (imageBytes[1] & 0xFF) == 0x00
                    && (imageBytes[2] & 0xFF) == 0x01 && (imageBytes[3] & 0xFF) == 0x00) {
                return Base64ImageType.ICO;
            }
            if ((imageBytes[0] & 0xFF) == 0x3C && (imageBytes[1] & 0xFF) == 0x73
                    && (imageBytes[2] & 0xFF) == 0x76 && (imageBytes[3] & 0xFF) == 0x67) {
                return Base64ImageType.SVG;
            }
            if ((imageBytes[0] & 0xFF) == 0x49 && (imageBytes[1] & 0xFF) == 0x49
                    && (imageBytes[2] & 0xFF) == 0x2A && (imageBytes[3] & 0xFF) == 0x00) {
                return Base64ImageType.TIF;
            }
            if (imageBytes.length > 12) {
                if ((imageBytes[0] & 0xFF) == 0x52 && (imageBytes[1] & 0xFF) == 0x49
                        && (imageBytes[2] & 0xFF) == 0x46 && (imageBytes[3] & 0xFF) == 0x46) {
                    return Base64ImageType.WEBP;
                }
            }
        }
        return Base64ImageType.UNKNOWN;
    }

}
