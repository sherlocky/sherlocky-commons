package com.sherlocky.common.util;

/**
 * @author zhangxu
 * @date 2022/3/20
 */
public class UrlUtils {

    /**
     * <p>获取URL中相对于域名的绝对地址</p>
     * <p>
     * 例如：
     * https://uniedudev.oss-cn-north-2.unicloudsrv.com/2020/09/10/e12d889dfb65501daa9b66f50a474fbe.ts
     * -->
     * /2020/09/10/e12d889dfb65501daa9b66f50a474fbe.ts
     * </p>
     * <p>
     * 例如：
     * https://ydxx.sz.edu.cn/szyxai/2020/09/10/e12d889dfb65501daa9b66f50a474fbe.ts
     * -->
     * /szyxai/2020/09/10/e12d889dfb65501daa9b66f50a474fbe.ts
     * </p>
     * @param url
     * @return
     */
    private String getRelativePathFromUrl(String url) {
        if (StrUtil.isBlank(url) || !url.startsWith("http")) {
            return url;
        }
        int pathSeparatorIndex = url.startsWith(HTTPS_SCHEMA_PREFIX) ?
                StrUtil.indexOf(url, "/", HTTPS_SCHEMA_PREFIX.length(), false)
                :
                StrUtil.indexOf(url, "/", HTTP_SCHEMA_PREFIX.length(), false)
                ;
        return StrUtil.subSuf(url, pathSeparatorIndex);
    }
}
