package com.sherlocky.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 正则相关工具类
 * @date 2022/1/21
 * @since 0.7.0
 */
public class RegexUtils {
    /**
     * 给定内容是否匹配正则
     *
     * @param regex 正则
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     * @since 0.7.0
     */
    public static boolean isMatch(String regex, CharSequence content) {
        if (content == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        if (StringUtils.isEmpty(regex)) {
            // 正则不存在则为全匹配
            return true;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return isMatch(pattern, content);
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     * @since 0.7.0
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }
}
