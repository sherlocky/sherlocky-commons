package com.sherlocky.common.util;

import org.apache.commons.lang3.StringUtils;

import static com.sherlocky.common.constant.CommonConstants.HTTPS_SCHEMA_PREFIX;
import static com.sherlocky.common.constant.CommonConstants.HTTP_SCHEMA_PREFIX;

/**
 * URL工具类
 * @date 2022/4/3
 */
public final class UrlUtils {

    private UrlUtils() {}

    /**
     * <p>获取URL中相对于域名的绝对地址</p>
     * <p>
     * 例如：
     * https://sherlocky.github.com/2022/03/30/index.html
     * -->
     * /2022/03/30/index.html
     * </p>
     * @param url
     * @return
     */
    public static String getRelativePathFromUrl(String url) {
        if (StringUtils.isBlank(url) || !url.startsWith("http")) {
            return url;
        }
        int pathSeparatorIndex = url.startsWith(HTTPS_SCHEMA_PREFIX) ?
                StringUtils.indexOf(url, "/", HTTPS_SCHEMA_PREFIX.length())
                :
                StringUtils.indexOf(url, "/", HTTP_SCHEMA_PREFIX.length())
                ;
        return StringUtils.substring(url, pathSeparatorIndex);
    }
}
