package com.sherlocky.common.image;

import com.sherlocky.common.constant.StrPool;
import com.sherlocky.common.core.enumeration.BaseEnum;
import com.sherlocky.common.util.StringUtils;

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
}
