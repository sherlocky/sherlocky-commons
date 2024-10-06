package com.sherlocky.common.image;

import com.sherlocky.common.core.enumeration.BaseEnum;

/**
 * Base图片类型枚举
 */
public enum Base64ImageType implements BaseEnum {
    JPG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    BMP("bmp", "image/bmp"),
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
}
